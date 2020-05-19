log.info("##Entering PRE PROCESSOR##");

LinkedList itemsArray1 = vars.getObject("itemsArray1");
LinkedList itemsArray2 = vars.getObject("itemsArray2");

if (itemsArray1 == null) {
    try {
        itemsArray1 = new LinkedList();
        vars.putObject("itemsArray1", itemsArray1);
        vars.put("request1", "(RequestBody:[" + ""  + "]");
        itemsArray2 = new LinkedList();
        vars.putObject("itemsArray2", itemsArray2);
        vars.put("request2", "(RequestBody:[" + ""  + "]");
    }
    catch (Exception e) {
        e.printStackTrace();
        log.info(e);        
    }
} else {  
    String string1 = "";
        for (int i = 0; i < itemsArray1.size(); i++) {           
            if (i >= 1) {
                string1 = string1 + ",";
            }
            string1 = string1 + itemsArray1.get(i).toString();
        }
        vars.put("request1", "(RequestBody:[" + string1 + "]");
    
    String string2 = "";
        for (int i = 0; i < itemsArray2.size(); i++) {           
            if (i >= 1) {
                string2 = string2 + ",";
            }
            string2 = string2 + itemsArray2.get(i).toString();
        }
        vars.put("request2", "(RequestBody:[" + string1 + "]");
}
