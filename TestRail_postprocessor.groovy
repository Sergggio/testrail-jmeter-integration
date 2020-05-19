import org.apache.jmeter.util.JMeterUtils;

log.info("##Entering POST PROCESSOR##");

LinkedList itemsArray1 = (LinkedList) vars.getObject("itemsArray1");
LinkedList itemsArray2 = (LinkedList) vars.getObject("itemsArray2");

String sampleName = ctx.getCurrentSampler().getName();
String threadGroup = ctx.getThreadGroup().getName();
JMeterUtils.setProperty("threadGroup", threadGroup);
// Regexp to cut number of test suite and test cases from Thread Group name and from HTTP request names
def pattern = ~/\[(.*?)\]/

// Get suite_id for TestRail report and save suite_id variable to use it later
def cutSuiteId = (threadGroup =~ pattern);
def suite_id = cutSuiteId[0][1].substring(1);
JMeterUtils.setProperty("suite_id", suite_id);

def cutCaseId = (sampleName =~ pattern);
def case_id = cutCaseId[0][1].substring(1);

def responseComment = prev.getResponseDataAsString().toString();
def responseCommentEdited = responseComment.replaceAll('"','\\\\"');

// Add result (status_id) to test cases
if (prev.isSuccessful()) {
	status_id = "1";
	println("Result: Passed");
} else {
	status_id = "5";
	println("Result: Failed");
}

// Configure string with test cases and its statuses for TestRail report
String casesAndResult = '{"case_id": ' + case_id + ', "status_id": ' + status_id + ', "comment": "' + responseCommentEdited + '"}';
// Configure string with test cases only
String onlyCases = case_id;

try {
    log.info("Added..");
    itemsArray1.add(casesAndResult);
    itemsArray2.add(onlyCases);
}
catch (Exception e) {
    e.printStackTrace();
    log.info(e);
}

testCasesList = itemsArray1.toString();
testCasesWithResult = itemsArray2.toString();

// Save variables to use it later
JMeterUtils.setProperty("testCasesList", testCasesList);
JMeterUtils.setProperty("testCasesWithResult", testCasesWithResult);
