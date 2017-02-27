// 深度复制
function clone(obj) {
  // Handle the 3 simple types, and null or undefined
  if (null == obj || "object" != typeof obj) return obj;
 
  // Handle Date
  if (obj instanceof Date) {
    var copy = new Date();
    copy.setTime(obj.getTime());
    return copy;
  }
 
  // Handle Array
  if (obj instanceof Array) {
    var copy = [];
    for (var i = 0; i < obj.length; ++i) {
      copy[i] = clone(obj[i]);
    }
    return copy;
  }
 
  // Handle Object
  if (obj instanceof Object) {
    var copy = {};
    for (var attr in obj) {
      if (obj.hasOwnProperty(attr)) copy[attr] = clone(obj[attr]);
    }
    return copy;
  }
 
  throw new Error("Unable to copy obj! Its type isn't supported.");
}

// 选择图片后的操作
var handle;// eitor的句柄
function selectImg(id) {
	var imageElement = handle.document.createElement( 'img' );
    imageElement.setAttribute( 'alt', '来源图片库' );
    imageElement.setAttribute( 'src', id );
    handle.insertElement( imageElement );
    CKEDITOR.dialog.getCurrent().hide();
}
// 分页
function paginate(totalCount, pageIndex, totalPage) {
    var _html = '';
    _html +=
        '<div class="form-inline">'+
            '<div class="mb20-box paging">'+
                '<div class="fl">'+
                   '<div style="line-height: 30px;margin-bottom: 2px;">';
    if(totalPage == 1){
        _html += '当前第<a class="btn btn-primary page-active" href="javascript:void(0);">'+pageIndex+'</a>页';
    }
    else if (totalPage > 1){
        if (pageIndex == 1){
            _html += '<a style="border: 1px solid transparent;margin-right: 10px; border-radius: 4px; padding: 6px 12px; font-size: 14px; vertical-align: middle; color: #fff; background-color: #428bca; border-color: #357ebd;" title="首页" href="javascript:void(0);">首页</a>';
            _html +='当前第<a style="border: 1px solid transparent;border-radius: 4px; padding: 6px 12px; font-size: 14px; vertical-align: middle; color: #000; background-color: #fff; border-color: #357ebd;" href="javascript:void(0);">'+pageIndex+'</a>页&nbsp;&nbsp;';
            var nextPage = pageIndex + 1;
            _html += '<a style="border: 1px solid transparent;margin-right: 10px; border-radius: 4px;padding: 6px 12px; font-size: 14px; vertical-align: middle; color: #fff; background-color: #428bca; border-color: #357ebd;" title="下一页" href="#" onclick="changePage('+nextPage+')" data-index="'+nextPage+'">下一页</a>';
            _html += '<a style="border: 1px solid transparent;margin-right: 10px; border-radius: 4px;padding: 6px 12px; font-size: 14px; vertical-align: middle; color: #fff; background-color: #428bca; border-color: #357ebd;" title="尾页" href="#" onclick="changePage('+totalPage+')"  data-index="'+totalPage+'">尾页</a>';
        }
        else if (pageIndex > 1){
            _html += '<a style="border: 1px solid transparent;margin-right: 10px; border-radius: 4px;padding: 6px 12px; font-size: 14px; vertical-align: middle; color: #fff; background-color: #428bca; border-color: #357ebd;" title="首页" href="#" onclick="changePage(1)"  data-index="1">首页</a>';
            var prePage = pageIndex -1;
            if (prePage >= 1){
            	_html += '<a style="border: 1px solid transparent;margin-right: 10px; border-radius: 4px;padding: 6px 12px; font-size: 14px; vertical-align: middle; color: #fff; background-color: #428bca; border-color: #357ebd;" title="上一页" href="#" onclick="changePage('+prePage+')"  data-index="'+prePage+'">上一页</a>';
            }
            _html +='当前第<a style="border: 1px solid transparent;border-radius: 4px; padding: 6px 12px; font-size: 14px; vertical-align: middle; color: #000; background-color: #fff; border-color: #357ebd;" href="javascript:void(0);">'+pageIndex+'</a>页&nbsp;&nbsp;';
            var nextPage = pageIndex + 1;
            if (nextPage > totalPage){
            	_html += '<a style="border: 1px solid transparent;margin-right: 10px; border-radius: 4px;padding: 6px 12px; font-size: 14px; vertical-align: middle; color: #fff; background-color: #428bca; border-color: #357ebd;" title="尾页" href="#" >尾页</a>';
            }
            else{
            	_html += '<a style="border: 1px solid transparent;margin-right: 10px; border-radius: 4px;padding: 6px 12px; font-size: 14px; vertical-align: middle; color: #fff; background-color: #428bca; border-color: #357ebd;" title="下一页" href="#" onclick="changePage('+nextPage+')"  data-index="'+nextPage+'">下一页</a>';
            	_html += '<a style="border: 1px solid transparent;margin-right: 10px; border-radius: 4px;padding: 6px 12px; font-size: 14px; vertical-align: middle; color: #fff; background-color: #428bca; border-color: #357ebd;" title="尾页" href="#"  onclick="changePage('+totalPage+')" data-index="'+totalPage+'">尾页</a>';
            }
        }
        _html += '<i class="first" style="margin-left:5px;">共'+totalPage+'页        '+totalCount+'条记录</i>';
    }
    _html +='</div></div></div></div>';
    return _html;
}

function changePage(pageIndex) {
	var params = clone(searchData);
	params['pageNo'] = pageIndex;
	initPage(JSON.stringify(params));
}
function initPage(params) {
    var element = document.getElementById('myDiv');
    CKEDITOR.ajax.post('/common/image/list.html', params, 'application/json', function( data ) {
        var _html = '<div><table style="width: 100%;border: 1px solid #ddd;margin-bottom: 20px;">';
        _html += '<thead style="background-color: #f3f2f8;"><tr><th style="text-align: center; padding: 8px; line-height: 1.42857143;width:200px;">文件名</th>';
        _html += '<th style="text-align: center; padding: 8px; line-height: 1.42857143; width: 60px;">图片</th>';
        _html += '<th style="text-align: center; padding: 8px; line-height: 1.42857143;">所属栏目</th>';
        _html += '<th style="text-align: center; padding: 8px; line-height: 1.42857143; width: 150px;">区域</th>';
        _html += '<th style="text-align: center; padding: 8px; line-height: 1.42857143; width: 200px;">关键词</th>';
        _html += '<th style="text-align: center; padding: 8px; line-height: 1.42857143; width: 60px;">操作</th></tr></thead>';
        _html += '<tbody>';
        data = JSON.parse(data);
        var pageIndex = 0;
        var totalCount = 0;
        var totalPage = 0;
        var pageInfo = data.pageInfo;
        if(pageInfo){
            var list = pageInfo.pageResults;
        	pageIndex = pageInfo.currentPage;
        	totalCount = pageInfo.totalCount;
        	totalPage = pageInfo.totalPage;
            for (var i = 0; i < list.length; i++){
                _html += '<tr>';
                _html += '<td style="border: 1px solid #ddd; padding: 8px; line-height: 1.42857143; vertical-align: middle;">'+list[i].name+'</td>';
                _html += '<td style="border: 1px solid #ddd; padding: 8px; line-height: 1.42857143; vertical-align: middle;">';
                _html += '<img  style="width: 50px;" src="'+list[i].thumbnailUrl+'" /> ';
                _html += '</td>';
                _html += '<td style="border: 1px solid #ddd; padding: 8px; line-height: 1.42857143; vertical-align: middle;">'+list[i].sectionMergerName+'</td>';
                _html += '<td style="border: 1px solid #ddd; padding: 8px; line-height: 1.42857143; vertical-align: middle;">'+list[i].locationMergerName+'</td>';
                _html += '<td style="border: 1px solid #ddd; padding: 8px; line-height: 1.42857143; vertical-align: middle; ">'+list[i].keyword+'</td>';
                _html += '<td style="border: 1px solid #ddd; padding: 8px; line-height: 1.42857143; vertical-align: middle;">';
                _html += '<a style="color: #428bca; text-decoration: none;" title="点击使用该图片" href="#" onclick="selectImg(\''+list[i].originalUrl+'\')">使用</a></td>';
                _html += '</tr>';
            }
        }
        _html += '</tbody>';
        _html += '</table></div>';
        _html += paginate(totalCount,pageIndex,totalPage);
        element.innerHTML = _html;
    });
}
// 数据初始化
var provinceList = [];
function init(){
	var element = document.getElementById('province');
    CKEDITOR.ajax.post('/common/image/getProvince.html', null, null, function( data ) {
	    if(data){
	    	provinceList = JSON.parse(data);
	    	if(provinceList){
	    		var _html = '<option value="0"><img src="'+currentPath+'img/joinbottom.gif"/>全国</option>';
	    		for(var i = 0; i < provinceList.length; i++){
	    			_html += '<option value="'+provinceList[i].id+'">'+provinceList[i].name+'</option>';
	    		}
	    		element.innerHTML = _html;
	    	}
	    }
	});
    CKEDITOR.ajax.post('/common/image/getSection.html?level=1', null, 'application/json', function( data ) {
	    if(data){
	    	var sectionList = JSON.parse(data);
	    	if(sectionList){
	    		var _html = '<option value="0">请选择</option>';
	    		for(var i = 0; i < sectionList.length; i++){
	    			_html += '<option value="'+sectionList[i].id+'">'+sectionList[i].name+'</option>';
	    		}
	    		document.getElementById('section').innerHTML = _html;
	    	}
	    }
	});
    
}
//当前项目访问url路径
var currentPath = '../../dist/ckeditor/ckeditor/plugins/imagelib/dialogs/';
function changeProvicne(){
	var province = document.getElementById('province');
	if(province.value!='0'){
		var element = document.getElementById('city');
		CKEDITOR.ajax.post('/common/image/getCity.html?parentId='+province.value+'&levelType=2', null, 'application/json', function( data ) {
		    if(data){
		    	provinceList = JSON.parse(data);
		    	if(provinceList){
		    		var _html = '<option value="0">请选择</option>';
		    		for(var i = 0; i < provinceList.length; i++){
		    			_html += '<option value="'+provinceList[i].id+'">'+provinceList[i].name+'</option>';
		    		}
		    		element.innerHTML = _html;
		    		var county = document.getElementById('county');
		    		county.innerHTML = '<option value="0">请选择</option>';
		    	}
		    }
		});
	}
	else{
		var city = document.getElementById('city');
		city.innerHTML = '<option value="0">请选择</option>';
		var county = document.getElementById('county');
		county.innerHTML = '<option value="0">请选择</option>';
	}
}
function changeCity(){
	var city = document.getElementById('city');
	if(city.value!='0'){
		var element = document.getElementById('county');
		var params = JSON.stringify({parentId: Number(city.value), levelType: 3});
		params = JSON.parse(params);
		CKEDITOR.ajax.post('/common/image/getCounty.html?parentId='+city.value+'&levelType=3', null, 'application/json', function( data ) {
		    if(data){
		    	provinceList = JSON.parse(data);
		    	if(provinceList){
		    		var _html = '<option value="0">请选择</option>';
		    		for(var i = 0; i < provinceList.length; i++){
		    			_html += '<option value="'+provinceList[i].id+'">'+provinceList[i].name+'</option>';
		    		}
		    		element.innerHTML = _html;
		    	}
		    }
		});
	} else {
		var county = document.getElementById('county');
		county.innerHTML = '<option value="0">请选择</option>';
	}
}
// 栏目
function changeSection(){
	var section = document.getElementById('section');
	if(section.value!='0'){
		var element = document.getElementById('section2');
		CKEDITOR.ajax.post('/common/image/getSection.html?level=2&parentId='+section.value, null, 'application/json', function( data ) {
			if(data){
		    	var list = JSON.parse(data);
		    	if(list){
		    		var _html = '<option value="0">请选择</option>';
		    		for(var i = 0; i < list.length; i++){
		    			_html += '<option value="'+list[i].id+'">'+list[i].name+'</option>';
		    		}
		    		element.innerHTML = _html;
		    		var section3 = document.getElementById('section3');
		    		section3.innerHTML = '<option value="0">请选择</option>';
		    	}
		    }
		});
	}
	else{
		var section2 = document.getElementById('section2');
		section2.innerHTML = '<option value="0">请选择</option>';
		var section3 = document.getElementById('section3');
		section3.innerHTML = '<option value="0">请选择</option>';
	}
}
function changeSection2(){
	var section = document.getElementById('section2');
	if(section.value!='0'){
		var element = document.getElementById('section3');
		CKEDITOR.ajax.post('/common/image/getSection.html?level=3&parentId='+section.value, null, 'application/json', function( data ) {
			if(data){
		    	var list = JSON.parse(data);
		    	if(list){
		    		var _html = '<option value="0">请选择</option>';
		    		for(var i = 0; i < list.length; i++){
		    			_html += '<option value="'+list[i].id+'">'+list[i].name+'</option>';
		    		}
		    		element.innerHTML = _html;
		    	}
		    }
		});
	}
	else{
		var section3 = document.getElementById('section3');
		section3.innerHTML = '<option value="0">请选择</option>';
	}
}


// 搜索
var searchData = {};
function setSearchData(){
	searchData['name'] = document.getElementById('name').value;
	searchData['keyword'] = document.getElementById('keyword').value;
	searchData['sectionId'] = document.getElementById('section').value;
	if(document.getElementById('section2').value!='0'){
		searchData['sectionId'] = document.getElementById('section2').value;
	}
	if(document.getElementById('section3').value!='0'){
		searchData['sectionId'] = document.getElementById('section3').value;
	}
	searchData['provinceId'] = document.getElementById('province').value;
	searchData['cityId'] = document.getElementById('city').value;
	searchData['locationId'] = document.getElementById('county').value;
}
// 搜索的点击事件
function search(){
	setSearchData();// 重置搜索条件结果
	var params = searchData;
	params = JSON.stringify(params);
	initPage(params);
}
// 重置
function resetData(){
	document.getElementById('name').value = '';
	document.getElementById('keyword').value = '';
	document.getElementById('section').value = '0';
	document.getElementById('section2').value = '0';
	document.getElementById('section2').innerHTML = '<option value="0">请选择</option>';
	document.getElementById('section3').value = '0';
	document.getElementById('section3').innerHTML = '<option value="0">请选择</option>';
	document.getElementById('province').value = '0';
	document.getElementById('city').value = '0';
	document.getElementById('city').innerHTML = '<option value="0">请选择</option>';
	document.getElementById('county').value = '0';
	document.getElementById('county').innerHTML = '<option value="0">请选择</option>';
	searchData = {};// 重置搜索条件结果
}

(function () {
	
    function ImageLibDialog(editor) {

        return {
            title: '来源于图片库',
            minWidth: 900,
            minHeight: 480,
            buttons: [],
            /*buttons: [{
                type: 'button',
                id: 'someButtonID',
                label: '自定义Button',
                onClick: function () {
                    //alert('Custom Button');
                }
				},
				CKEDITOR.dialog.okButton,
				CKEDITOR.dialog.cancelButton
			],*/
            contents:
            [
                {
                    id: 'info',
                    label: '来源图片库',
                    title: '来源图片库',
                    elements:
                    [
                        {
							id: 'section',
							type: 'html',
							html: '<div style="width:100%;"><label>文件名：<input type="text" id="name" name="name" '
								+'style="width:120px;margin-right:10px; border: 1px solid #c9cccf;  font-size: 12px;  height: 23px;"/></label><label>关键词：<input type="text" '
								+'id="keyword" name="keyword" style="width:120px;margin-right:10px; border: 1px solid #c9cccf;  font-size: 12px;  height: 23px;"/></label>栏目：'
								+'<select style="width: 120px; margin-right:10px; border: 1px solid #c9cccf;  font-size: 12px;  height: 25px;vertical-align: middle;"  '
								+'id="section" name="section" onclick="changeSection();" ><option value="0">请选择</option></select><select style="width: 120px; margin-'
								+'right:10px; border: 1px solid #c9cccf;  font-size: 12px;  height: 25px;" id="section2" name="section2" onchange="changeSection2()"><option '
								+'value="0">请选择</option></select><select style="width: 120px; margin-right:10px; border: 1px solid #c9cccf;  font-size: 12px;  height: 25px;" '
								+'id="section3" name="section3"><option value="0">请选择</option></select></div>',
							setup: function( type, element ) {
								alert('setup');
							},
							commit: function( type, element ) {
								
							}
						},
						{
							id: 'location',
							type: 'html',
							html: '<div style="width:100%;">区&nbsp;&nbsp;&nbsp;&nbsp;域：<select style="width: '
								+'120px; margin-right:10px; border: 1px solid #c9cccf;  font-size: 12px;  height: 25px;" id="province" name="province" onchange="changeProvicne()">'
								+'<option value="0">请选择</option></select><select style="width: 120px; margin-right:10px; border: 1px solid #c9cccf;  font-size: 12px;  '
								+'height: 25px;" id="city" name="city" onchange="changeCity()"><option value="0">请选择</option></select><select style="width: 120px; margin-'
								+'right:10px; border: 1px solid #c9cccf;  font-size: 12px;  height: 25px;" id="county" name="county"><option value="0">请选择</option></select><a '
								+'style="border: 1px solid transparent;margin-right: 10px; border-radius: 4px; padding: 6px 12px; font-size: 14px; vertical-align: middle; color: '
								+'#fff; background-color: #428bca; border-color: #357ebd;" href="javascript:search();">搜索</a>&nbsp;&nbsp;<a style="border: 1px solid '
								+'transparent;margin-right: 10px; border-radius: 4px; padding: 6px 12px; font-size: 14px; vertical-align: middle; color: #357ebd; background-'
								+'color: #fff; border-color: #357ebd;" href="javascript:resetData();">重置</a></div>',
							setup: function( type, element ) {
								alert('setup');
							},
							commit: function( type, element ) {
								
							}
						},						
						{
							id: 'mainContent',
							type: 'html',
							html: '<div id="myDiv"></div>',
							label: '内容',
							setup: function( type, element ) {
								alert('setup');
							},
							onLoad: function (me, element) {

							},
							commit: function( type, element ) {
								
							}
						}
                    ]
                }
            ],
            onLoad: function () {
                // 从后台请求数据
            	search();
                // 初始化搜索数据
                init();                            
            },
            onShow: function () {
                //alert('onShow');
            },
            onHide: function () {
                //alert('onHide');
            },
            onOk: function () {
                this.commitContent();
            },
            onCancel: function () {
                //alert('onCancel');
            },
            resizable: CKEDITOR.DIALOG_RESIZE_BOTH
        };
    }

    CKEDITOR.dialog.add('imagelib', function (editor) {
    	handle = editor;
        return ImageLibDialog(editor);
    });
})();
