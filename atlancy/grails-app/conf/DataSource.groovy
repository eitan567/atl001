dataSource {
	pooled = true
	//driverClassName = "org.postgresql.Driver"
	driverClassName = "com.mysql.jdbc.Driver"
	//dialect = org.hibernate.dialect.PostgreSQLDialect
	dialect = org.hibernate.dialect.MySQL5InnoDBDialect
	//username = "puxxbzzyskthea"
	//password = "QKfbkg-DQ4STvFIsbShpsB_UOL"
	//logSql = true
	
	properties {
		maxActive = 50
		maxIdle = 25
		minIdle = 1
		initialSize = 1
   
		numTestsPerEvictionRun = 3
		maxWait = 10000
   
		testOnBorrow = true
		testWhileIdle = true
		testOnReturn = true
   
		validationQuery = "select now()"
   
		minEvictableIdleTimeMillis = 1000 * 60 * 5
		timeBetweenEvictionRunsMillis = 1000 * 60 * 5
	 }
}
hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = true
	cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
	development {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			// = "jdbc:mysql://remote-mysql3.servage.net:3306/trixico567?useUnicode=yes&characterEncoding=UTF-8&collation=utf8mb4_general_ci"
			//username = "trixico567"
			//password = "567567567"
			
			//url="jdbc:postgresql://ec2-107-20-220-11.compute-1.amazonaws.com:5432/d1ssepvfjbk60g"
			//username = "puxxbzzyskthea"
			//password = "QKfbkg-DQ4STvFIsbShpsB_UOL"
			
			
//			url = "jdbc:mysql://us-cdbr-east-04.cleardb.com/heroku_6a0356c2d5724f7?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_general_ci"
//			username = "b9ffc6703dfbc2"
//			password = "41b44e53" 
			
			url = "jdbc:mysql://localhost:3306/atlancy_db?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_general_ci"
			username = "root"
			password = ""
			
		}
		hibernate {
			show_sql = true
		}
	}
	test {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
//			url = "jdbc:mysql://us-cdbr-east-04.cleardb.com/heroku_6a0356c2d5724f7?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_general_ci"
//			username = "b9ffc6703dfbc2"
//			password = "41b44e53"
			
			//url="jdbc:postgresql://ec2-107-20-220-11.compute-1.amazonaws.com:5432/d1ssepvfjbk60g"
			//username = "puxxbzzyskthea"
			//password = "QKfbkg-DQ4STvFIsbShpsB_UOL"
			
			url = "jdbc:mysql://localhost:3306/atlancy_db?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_general_ci"
			username = "root"
			password = ""
		}
		hibernate {
			show_sql = true
		}
	}
	production {
		dataSource {
			dbCreate = "update"
//			url = "jdbc:mysql://us-cdbr-east-04.cleardb.com/heroku_6a0356c2d5724f7?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_general_ci"
//			username = "b9ffc6703dfbc2"
//			password = "41b44e53"
			
			url = "jdbc:mysql://localhost:3306/atlancy_db?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_general_ci"
			username = "root"
			password = ""
			
			//url="jdbc:postgresql://ec2-107-20-220-11.compute-1.amazonaws.com:5432/d1ssepvfjbk60g"
			//username = "puxxbzzyskthea"
			//password = "QKfbkg-DQ4STvFIsbShpsB_UOL"
		}
		hibernate {
			show_sql = true
		}
	}
}

//dataSource {
//    pooled = true
//    driverClassName = "org.h2.Driver"
//    username = "sa"
//    password = ""
//}
//hibernate {
//    cache.use_second_level_cache = true
//    cache.use_query_cache = false
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
//}
//// environment specific settings
//environments {
//    development {
//        dataSource {
//            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
//            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
//        }
//    }
//    test {
//        dataSource {
//            dbCreate = "update"
//            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
//        }
//    }
//    production {
//        dataSource {
//            dbCreate = "update"
//            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
//            pooled = true
//            properties {
//               maxActive = -1
//               minEvictableIdleTimeMillis=1800000
//               timeBetweenEvictionRunsMillis=1800000
//               numTestsPerEvictionRun=3
//               testOnBorrow=true
//               testWhileIdle=true
//               testOnReturn=true
//               validationQuery="SELECT 1"
//            }
//        }
//    }
//}
