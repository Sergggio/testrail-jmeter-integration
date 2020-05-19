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
`${__BeanShell(import org.apache.jmeter.services.FileServer; FileServer.getFileServer().getBaseDir();)}${__BeanShell(File.separator,)}TestRail_preprocessor.groovy`

### Step 4. JMeter: Add to your Thread Group "JSR223 PostProcessor"
Add - Post Processors - JSR223 PostProcessor<br/>
Add to Script file - File Name:<br/>
`${__BeanShell(import org.apache.jmeter.services.FileServer; FileServer.getFileServer().getBaseDir();)}${__BeanShell(File.separator,)}TestRail_postprocessor.groovy`

### Step 5. JMeter: Add to your Thread Group "Include Controller"
(Add - Logic Controller - Include Controller)<br/>
Add to Script file - File Name:<br/>
`${__BeanShell(import org.apache.jmeter.services.FileServer; FileServer.getFileServer().getBaseDir();)}${__BeanShell(File.separator,)}TestRail_integration.jmx`

### Step 6. JMeter: Open TestRail_integration.jmx and configure it<br/>
* add HTTP Header Manager and put Content-Type "application/json"
* if you want to make your sending report optional, you can use variable "reportToTR" and "If controller"
* open HTTP Request "POST Test Run" and add server name, add your Basic authorization to its HTTP Header Manager
* open HTTP Request "POST Add result" and add server name, add your Basic authorization to its HTTP Header Manager
* open HTTP Request "POST Close Run" and add server name, add your Basic authorization to its HTTP Header Manager

### Step 7. JMeter: Configure test file<br/>
Return to your JMeter test file. It's structure is:
* Thread Group [S1]
* User defined variables ("reportToTR" boolean variable and "project_id" - your TestRail project ID)
* HTTP Header Manager: add your Basic authorization and "Security token"
* HTTP Requests: "GET request [C1]", "POST request [C2]"
* JSR223 PreProcessor
* JSR223 PostProcessor
* Include controller
