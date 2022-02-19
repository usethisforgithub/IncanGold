public enum Card{
    TREASURE1, 
    TREASURE2,
    TREASURE3,
    TREASURE4, 
    TREASURE5, 
    TREASURE7, 
    TREASURE9, 
    TREASURE11,
    TREASURE13,
    TREASURE14,
    TREASURE15,
    TREASURE17,
    HAZARD1,
    HAZARD2,
    HAZARD3,
    HAZARD4,
    HAZARD5,
    ARTIFACT;

    public static boolean isHazard(Card card){
        return(
            card == HAZARD1 || 
            card == HAZARD2 ||
            card == HAZARD3 ||
            card == HAZARD4 || 
            card == HAZARD5
        );
    }
}