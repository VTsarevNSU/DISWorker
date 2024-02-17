package fit.g20202.tsarev.DISLabSBWorker.DTO;

public record TaskFromManagerDTO(
        String requestId, String hash, String firstSymbolPos, String lastSymbolPos, String maxLength
) {}
