package com.example.demo.generatePassword;

import org.springframework.lang.Nullable;

import com.example.demo.entity.PasswordConfigEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PasswordConfigForm(
	@NotNull
    @Min(value=4)
    @Max(value=26)
    Integer length,
    
    @Nullable
    Boolean includeSymbols,
    
    @Nullable
    Boolean includeLowercase,
    
    @Nullable
    Boolean includeUppercase,
    
    @Nullable
    Boolean includeNumbers,
    
    @Nullable
    String customCharacters) {

    public PasswordConfigForm() {
        this(12, false, true, true, false, "");
    }
    
    public PasswordConfigForm(Integer length, Boolean includeSymbols, Boolean includeLowercase, 
                               Boolean includeUppercase, Boolean includeNumbers,
                               String customCharacters) {
    	
    	// null check
    	if(length == null) length = 12;
		if(includeSymbols == null) includeSymbols = false;
    	if(includeLowercase == null) includeLowercase = false;
    	if(includeUppercase == null) includeUppercase = false;
    	if(includeNumbers == null) includeNumbers = false;
    	if(customCharacters == null) customCharacters = "";
    	
    	this.length = length;
    	this.includeSymbols = includeSymbols;
    	this.includeLowercase = includeLowercase;
    	this.includeUppercase = includeUppercase;
		this.includeNumbers = includeNumbers;
		this.customCharacters = customCharacters;
    }
    
    // Entityに変換
    public PasswordConfigEntity toEntity() {
    	
        return new PasswordConfigEntity(length, includeSymbols, includeLowercase, includeUppercase,
        		includeNumbers, customCharacters);
    }
}
