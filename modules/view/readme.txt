2014-07-29
去掉plugin，让view、core、persist可以独立运行
解决方案：
	每个需要aotoWired的变量，如果没有默认实现类，都在插件的情况下，将变量改为List形式
	提供默认实现类
	每个List<接口>  变量名， 系统提供一个默认实现类，不做任何事情；
	后期有插件时，加入插件的实现类，此时不会出现问题，以解决插件插拔问题。

	
	CowSwingCacheManager   提供默认实现类DefaultCowSwingCache
		@Autowired
		private Map<String,ICowSwingCache<?>> cowSwingCacheMap;
	DisposeServiceManager   提供默认实现类
		@Autowired
		private List<DisposeService> disposeServiceList;
		
	InitServiceManager     提供默认实现类
		@Autowired
		private List<InitService> initServiceList;	

2014-12-4  进行精确拆分
引入全局属性文件，启动时读取此文件进行解析

独立mybatisconfig：每个插件有一个配置文件
独立spring配置文件独立出来：每个插件有一个配置文件

争取使组件能够独立运行，独立调试；


12-12  修改pluginsloader类，实现插件加载
将plugins下面的每个目录作为一个插件，插件下面lib包加载进classpath当中

下一步需要将context和mybatis的config文件抽出来，放到plugins下面每个目录下的config里面

12-16 
改造为插件模式

12-17
插件模式改造完成，后续需要详细跑流程，弄清后台数据结构
逐步用pms的数据结构进行适配




