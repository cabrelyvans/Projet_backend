package com.example.util;

import com.example.dto.BanRequestDto;
import com.example.entity.Alert;

public class DtoEntityUtil {
	

    public static Alert profilDToToProfil(BanRequestDto profilDto) {
    	
    	Alert profil = new Alert();
    	
    	profil.setName(profilDto.getName());
    	
    	return profil;
    	
    }

}
