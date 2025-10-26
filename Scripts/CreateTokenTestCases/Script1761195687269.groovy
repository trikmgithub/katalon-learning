import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.github.fge.jsonschema.library.Keyword as Keyword
import com.github.fge.jsonschema.library.KeywordBuilder as KeywordBuilder
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import groovy.json.JsonOutput as JsonOutput
import internal.GlobalVariable as GlobalVariable
import org.apache.commons.collections4.map.HashedMap as HashedMap
import org.apache.groovy.parser.antlr4.GroovyParser.KeywordsContext as KeywordsContext
import org.openqa.selenium.Keys as Keys

def objectRepository = findTestObject('CreateToken', [
	('body') : """
    {
        "username": "${username}",
        "password": "${password}"
    }
    """
])

KeywordUtil.logInfo("Request body:\n" + objectRepository.getVariables()['body'])

def response = WS.sendRequest(objectRepository)

WS.verifyResponseStatusCode(response, 200)

