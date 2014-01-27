package atlancy

class RedirectMeTagLib {

	def redirectPage={ attrs->
		def url=attrs.get('url')
		 response.sendRedirect("${request.contextPath}"+url)
	  }
	
}
