import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.github.fge.jsonschema.library.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.json.JsonSlurper
import internal.GlobalVariable

import org.apache.commons.collections4.map.HashedMap
import org.openqa.selenium.Keys as Keys

//String token = GlobalVariable.G_UserNameToken
//
//Map<String, Object> variables = new HashMap<>()
//
//variables.put('myToken', token)
//
//resp = WS.sendRequest(findTestObject('UpdateBooking', variables))
//
//WS.verifyResponseStatusCode(resp, Integer.parseInt(statusCode))


tokenObj = WS.sendRequest(findTestObject('CreateToken', [('username') : GlobalVariable.G_UserName, ('password') : GlobalVariable.G_Password]))

KeywordUtil.logInfo("${GlobalVariable.G_UserName}" + "${GlobalVariable.G_Password}")

WS.verifyResponseStatusCode(tokenObj, 200)

String token = WS.getElementPropertyValue(tokenObj, 'token')

Map<String, Object> variables = new HashMap<>()

variables.put('myToken', token)

resp = WS.sendRequest(findTestObject('UpdateBooking', variables))

String resBody = resp.getResponseText()

KeywordUtil.logInfo(resBody)

WS.verifyResponseStatusCode(resp, Integer.parseInt(statusCode))


