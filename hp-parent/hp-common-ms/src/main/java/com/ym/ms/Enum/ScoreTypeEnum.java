package com.ym.ms.Enum;

public enum ScoreTypeEnum {
	/**
	 * 每次登录
	 */
	Eeverylogin("每次登录",1,1),
	/**
	 * 连续2次
	 */
	ContinueTwoLogin("连续2次登录",2,2),
	/**
	 * 连续3次
	 */
	ContinueThreeLogin("连续3次登录",3,3),
	/**
	 * 连续4次
	 */
	ContinueFourLogin("连续4次登录",4 ,4),
	/**
	 * 连续5次
	 */
	ContinueFiveLogin("连续5次登录",5 ,5),
	/**
	 * 连续6次
	 */
	ContinueSixLogin("连续6次登录",6 ,6),
	/**
	 * 连续7次
	 */
	ContinuesevenLogin("连续7次登录",7,7),

	/**
	 * 每次喝水达标
	 */
	EeveryDrinkStandard("每次喝水达标",8,1),


	/***
	 * 连续3次喝水达标
	 */
	ContineThreeDrinkStandard(" 连续3次喝水达标",9 ,3),

	/***
	 * 连续5次喝水达标
	 */
	ContineFiveDrinkStandard(" 连续5次喝水达标",10,5),
	/***
	 * 连续10次喝水达标
	 */
	ContineTenDrinkStandard(" 连续10次喝水达标",11 ,10),

	/***
	 * 连续20次喝水达标
	 */
	ContineTwoweenDrinkStandard(" 连续20次喝水达标",12,20),
	/***
	 * 连续30次喝水达标
	 */
	ContineThirtyDrinkStandard(" 连续30次喝水达标",13,30),

	/***
	 * 连续40次喝水达标
	 */
	ContineFivetyDrinkStandard(" 连续40次喝水达标",14,40),
	
	
	/***
	 * 连续80次喝水达标
	 */
	ContineEeightDrinkStandard(" 连续80次喝水达标",15,80),
	
	
	/***
	 * 连续100次喝水达标
	 */
	ContineOnehundredStandard(" 连续80次喝水达标",16,100),
	/***
	 * 群组排名冠军
	 */
	FirstPlace("群组排名冠军",17,-1),
	/***
	 * 群组排名亚军
	 */
	SecondPlace("群组排名亚军",18 ,-1),
	/***
	 * 群组排名季军
	 */
	threePlace("群组排名季军",19,-1);
	 private String name;  
    private Integer index;  
    
    private  int days;
    // 构造方法  
    private ScoreTypeEnum(String name, int index  ,int days) {  
        this.name = name;  
        this.days=days;
        this.index = index;  
    }  
    
     public  Integer getIndex() {
		return index;
	}
     public  String getIName() {
 		return name;
 	}
     public int getDays() {
		
    	return days;
	}
     
    @Override  
    public String toString() {  
        return this.index+"|"+this.name;  
    }  
}


 
