package com.example.util;

//... tes imports existants
import com.example.dto.GameLogDto;
import com.example.entity.GameLog;

public class DtoEntityUtil {

 // ... ta méthode profilDToToProfil existante ...

 public static GameLog gameLogDtoToEntity(GameLogDto dto) {
     GameLog log = new GameLog();
     log.setSource(dto.getSource());
     log.setPlayerId(dto.getPlayerId());
     log.setAction(dto.getAction());
     log.setMetadata(dto.getMetadata());
     return log;
 }
}
