# testrail-jmeter-integration
Example of sending JMeter test results to TestRail

### Step 1. TestRail: Create test suite and test cases
Example: <br/> `/index.php?/suites/view/1` - Test suite S1  
`/index.php?/cases/view/1` - Test case C1  
`/index.php?/cases/view/2` - Test case C2

### Step 2. JMeter: Add to your Thread Group name test suite id, for example "My Test Run [S1]"
Add to your HTTP Request names test case ids, for example `"Get request [C1]"`, `"Post request [C2]"`
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

