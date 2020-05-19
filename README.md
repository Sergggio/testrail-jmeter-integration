# testrail-jmeter-integration
Example of sending JMeter test results to TestRail

### Step 1. TestRail: Create test suite and test cases
Example: <br/> `/index.php?/suites/view/1` - Test suite S1  
`/index.php?/cases/view/1` - Test case C1  
`/index.php?/cases/view/2` - Test case C2

### Step 2. JMeter: Add to your Thread Group name test suite id, for example "My Test Run [S1]"
Add to your HTTP Request names test case ids, for example `"GET request [C1]"`, `"POST request [C2]"`
<br/>
Steps 3 - 5 are optional, you can add groovy directly to your .jmx script but if you have a lot of tests it will be painfully change code in every test. These steps can provide scalability.
<br/>
### Step 3. JMeter: Add to your Thread Group "JSR223 PreProcessor"
Add - Pre Processors - JSR223 PreProcessor<br/>
Add to Script file - File Name:<br/>
`${__BeanShell(import org.apache.jmeter.services.FileServer; FileServer.getFileServer().getBaseDir();)}${__BeanShell(File.separator,)}TestRail_preprocessor.groovy`<br/><br/>
![pre processor](../master/images/preprocessor.png)

### Step 4. JMeter: Add to your Thread Group "JSR223 PostProcessor"
Add - Post Processors - JSR223 PostProcessor<br/>
Add to Script file - File Name:<br/>
`${__BeanShell(import org.apache.jmeter.services.FileServer; FileServer.getFileServer().getBaseDir();)}${__BeanShell(File.separator,)}TestRail_postprocessor.groovy`<br/><br/>
![post processor](../master/images/postprocessor.png)

### Step 5. JMeter: Add to your Thread Group "Include Controller"
(Add - Logic Controller - Include Controller)<br/>
Include Test plan - File Name:<br/>
`${__BeanShell(import org.apache.jmeter.services.FileServer; FileServer.getFileServer().getBaseDir();)}${__BeanShell(File.separator,)}TestRail_integration.jmx`<br/><br/>
![include controller](../master/images/includecontroller.png)

### Step 6. JMeter: Open TestRail_integration.jmx and configure it<br/>
* add HTTP Header Manager and put Content-Type "application/json"
* if you don't want to send all your test executions to TestRail you can use "If controller" and boolean variable, for example "reportToTR":<br/><br/>
![if controller](../master/images/ifcontroller.png)<br/>
* open HTTP Request "POST Test Run" and add server name, add your Basic authorization to its HTTP Header Manager
* open HTTP Request "POST Add result" and add server name, add your Basic authorization to its HTTP Header Manager
* open HTTP Request "POST Close Run" and add server name, add your Basic authorization to its HTTP Header Manager

### Step 7. JMeter: Configure test file<br/>
Return to your JMeter test file. It's structure is:<br/>
![project structure](../master/images/project_structure.png)

* Thread Group [S1]
* User defined variables ("reportToTR" boolean variable and "project_id" - your TestRail project ID)
* HTTP Header Manager: add your Basic authorization and "Security token"
* HTTP Requests: "GET request [C1]", "POST request [C2]"
* JSR223 PreProcessor
* JSR223 PostProcessor
* Include controller

### Step 8. JMeter: Run your JMeter test and recieve a link to TestRail test run<br/>
Run your JMeter test and at the end you will recieve a direct link to TestRail test run in your console. Your failed test cases will contain error texts.

### Enjoy!
