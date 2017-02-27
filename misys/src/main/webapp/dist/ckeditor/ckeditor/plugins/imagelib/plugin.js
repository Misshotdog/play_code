CKEDITOR.plugins.add('imagelib', {
    init: function (editor) {
        var pluginName = 'imagelib';
        CKEDITOR.dialog.add(pluginName, this.path + 'dialogs/imagelib.js');
        editor.addCommand(pluginName, new CKEDITOR.dialogCommand(pluginName));
        editor.ui.addButton(pluginName,
        {
            label: '来源图片库',
            command: pluginName,
			icon:CKEDITOR.getUrl( this.path + 'images/imagelib.png' )
        });
    }
});