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
import groovy.json.JsonBuilder
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.json.JsonSlurper
import internal.GlobalVariable

import org.apache.commons.collections4.map.HashedMap
import org.openqa.selenium.Keys as Keys

def tokenRepository = findTestObject('CreateToken', [
	('body') : """
    {
        "username": "${GlobalVariable.G_UserName}",
        "password": "${GlobalVariable.G_Password}"
    }
    """
])

def tokenResponse = WS.sendRequest(tokenRepository)

WS.verifyResponseStatusCode(tokenResponse, 200)

def tokenResponseBody = tokenResponse.getResponseBodyContent()
def tokenResponseJson = new JsonSlurper().parseText(tokenResponseBody)

def tokenAuthen = tokenResponseJson.token

def createBookingJson = new JsonBuilder([
	firstname: "Jim",
	lastname: "Brown",
	totalprice: 111,
	depositpaid: true,
	bookingdates: [
		checkin: "2018-01-01",
		checkout: "2019-01-01"
	],
	additionalneeds: "Breakfast"
]).toPrettyString()

def createBookingRepository = findTestObject('CreateBooking', [
    ('body'): createBookingJson
])

KeywordUtil.logInfo("Create booking request body:\n" + createBookingRepository.getVariables()['body'])

def createBookingResponse = WS.sendRequest(createBookingRepository)

WS.verifyResponseStatusCode(createBookingResponse, 200)

def createBookingResponseBody = createBookingResponse.getResponseBodyContent()
def createBookingResponseJson = new JsonSlurper().parseText(createBookingResponseBody)

def bookingID = createBookingResponseJson.bookingid

def updateBookingJson = new JsonBuilder([
	firstname: firstName,
	lastname: lastName,
	totalprice: totalPrice as Double,
	depositpaid: depositPaid.toBoolean(),
	bookingdates: [
		checkin: checkin,
		checkout: checkout
	],
	additionalneeds: additionalNeeds
]).toPrettyString()


def updateBookingRepository = findTestObject('UpdateBooking', [
	('body'): updateBookingJson,
	('bookingID'): bookingID,
	('token'): tokenAuthen
])

KeywordUtil.logInfo("Update booking request body:\n" + updateBookingRepository.getVariables()['body'])

def updateBookingResponse = WS.sendRequest(updateBookingRepository)

def responseJson = new JsonSlurper().parseText(updateBookingResponse.getResponseText())

WS.verifyResponseStatusCode(updateBookingResponse, Integer.parseInt(statusCode))

WS.verifyElementPropertyValue(updateBookingResponse, 'firstname', firstName)
assert responseJson.firstname == firstName

WS.verifyElementPropertyValue(updateBookingResponse, 'lastname', lastName)

WS.verifyElementPropertyValue(updateBookingResponse, 'totalprice', totalPrice)

//WS.verifyElementPropertyValue(updateBookingResponse, 'depositpaid', depositPaid)

//WS.verifyElementPropertyValue(updateBookingResponse, 'checkin', checkin)

//WS.verifyElementPropertyValue(updateBookingResponse, 'checkout', checkout)

WS.verifyElementPropertyValue(updateBookingResponse, 'additionalneeds', additionalNeeds)





