package fit.g20202.tsarev.DISLabSBWorker.DTO;

import java.util.List;

public record ResultsToManagerDTO(String requestId, List<String> result, Integer part) {
}
