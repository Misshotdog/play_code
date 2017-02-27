/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	config.filebrowserWindowWidth = '800';  //“浏览服务器”弹出框的size设置
	config.filebrowserWindowHeight = '500';
	//config.filebrowserBrowseUrl = '/CKEditorDemo/ckfinder/ckfinder.html';
    //config.filebrowserImageBrowseUrl = '/CKEditorDemo/ckfinder/ckfinder.html?type=Images';
   // config.filebrowserFlashBrowseUrl = '/CKEditorDemo/ckfinder/ckfinder.html?type=Flash';
   // config.filebrowserUploadUrl = '/CKEditorDemo/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files';
   // config.filebrowserImageUploadUrl = '/CKEditorDemo/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
  //  config.filebrowserFlashUploadUrl = '/CKEditorDemo/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash';
    config.toolbarGroups = [
                    		{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
                    		{ name: 'forms', groups: [ 'forms' ] },
                    		{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
                    		{ name: 'insert', groups: [ 'insert' ] },
                    		{ name: 'links', groups: [ 'links' ] },
                    		'/',
                    		{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
                    		{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi', 'paragraph' ] },
                    		{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker', 'editing' ] },
                    		'/',
                    		{ name: 'styles', groups: [ 'styles' ] },
                    		{ name: 'colors', groups: [ 'colors' ] },
                    		{ name: 'tools', groups: [ 'tools' ] },
                    		{ name: 'others', groups: [ 'others' ] },
                    		{ name: 'about', groups: [ 'about' ] }
                    	];

	config.removeButtons = 'Source,Save,Print,Templates,Flash,PageBreak,About,Language';
	// 自定义插件
	config.extraPlugins = "imagelib,ajax";
};
