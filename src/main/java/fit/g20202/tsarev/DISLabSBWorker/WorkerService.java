package fit.g20202.tsarev.DISLabSBWorker;

import fit.g20202.tsarev.DISLabSBWorker.DTO.ResponseFromManagerDTO;
import fit.g20202.tsarev.DISLabSBWorker.DTO.ResultsToManagerDTO;
import fit.g20202.tsarev.DISLabSBWorker.DTO.TaskFromManagerDTO;
import org.paukov.combinatorics3.Generator;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class WorkerService {

    ExecutorService threadPool;
    List<String> alphabet;
    WorkerService(){
        threadPool = Executors.newFixedThreadPool(5);

        // create alphabet
        alphabet = new ArrayList<String>();
        for (char c = 'a'; c <= 'z'; c++){
            alphabet.add(Character.toString(c));
        }
        for (char c = '0'; c <= '9'; c++){
            alphabet.add(Character.toString(c));
        }

    }

    public void startCrack(TaskFromManagerDTO task){
        threadPool.submit(() -> {
            crackHash(
                    task.requestId(),
                    task.hash(),
                    Integer.parseInt(task.firstSymbolPos()),
                    Integer.parseInt(task.lastSymbolPos()),
                    Integer.parseInt(task.maxLength())
            );
        });
    }

    public void crackHash(String requestId, String target, Integer startSymbolPos, Integer endSymbolPos, Integer maxLength){

        List<String> result = new ArrayList<String>();

        System.out.println(requestId);
        System.out.println(target);
        System.out.println(alphabet.get(startSymbolPos));
        System.out.println(alphabet.get(endSymbolPos));
        System.out.println(maxLength);

        for (int len = 0; len < maxLength; len++){

            Generator.permutation(alphabet).withRepetitions(len).stream().forEach((wordAsList) -> {
                try {

                    for (int alphabetPos = startSymbolPos; alphabetPos <= endSymbolPos; alphabetPos++ ){

                        String word = alphabet.get(alphabetPos);
                        for (String c: wordAsList){
                            word = word.concat(c);
                        }

                        MessageDigest md = MessageDigest.getInstance( "MD5" );

                        byte[] digest = md.digest(word.getBytes());
                        BigInteger no = new BigInteger(1, digest);
                        String myHash = no.toString(16);
                        while (myHash.length() < 32) {
                            myHash = "0" + myHash;
                        }
                        if (myHash.equals(target)){
                            result.add(word);
                        }

                    }

                } catch ( NoSuchAlgorithmException e ) {
                    e.printStackTrace();
                }
            });

        }

        System.out.println("Worker finished");

        // send request to the worker
        RestTemplate restTemplate = new RestTemplate();

        // PATCH fix
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(factory);

        HttpEntity<ResultsToManagerDTO> request = new HttpEntity<>(new ResultsToManagerDTO(
                requestId, result
        ));

        ResponseFromManagerDTO response =
                restTemplate.patchForObject(
                        //"http://localhost:8080/internal/api/manager/hash/crack/request",
                        "http://manager:8080/internal/api/manager/hash/crack/request",
                        request,
                        ResponseFromManagerDTO.class);

    }

}
