class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{ constraints { // apply constraints here
			} }

		"/spy/gate"(controller: "spy",parseRequest:true) {
			action = [POST: "gate"]
		}

		"/spy/getdata"(controller: "spy") {
			action = [POST: "getdata"]
		}


		"/"(controller:"home",action:"/index")

		
		//	"500"(view:'/error')
		//	"404"(view:'/uaerror')
		
		//	"500"(view:'/error')
		//	"404"(view:'/404-page')
			
			"403"(controller: "error", action: "forbidden")
			"404"(controller: "error", action: "notFound")
			"500"(controller: "error", action: "internalError")
	}
}
