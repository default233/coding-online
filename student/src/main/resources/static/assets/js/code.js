const baseUrl = "/coding-online/student";

jQuery(document).ready(function() {

    var coder = CodeMirror.fromTextArea(document.getElementById("codeMirrorDemo"), {
        theme: 'monokai',
        mode: "text/x-java",
        lineNumbers: true,
        matchBrackets: true,
        hintOptions: { // 自定义提示选项
            completeSingle: false, // 当匹配只有一项的时候是否自动补全
        }
    });
    coder.on('keypress', function () {
        coder.showHint();
    });
    coder.on("blur", function(){
        coder.save();
    });
    $('#code-submit').click(function () {

        let codeSource = coder.getValue();
        let params = {
            'className': codeSource.substring(codeSource.indexOf("class") + 5,codeSource.indexOf("{")).trim(),
            'codeSource': codeSource
        }
        $.ajax({
            url: baseUrl + '/code-submit',
            type: 'post',
            data: JSON.stringify(params),
            contentType: 'application/json',
            dataType: "json",
            success: function (res) {

            },
            error: function (res) {

            }

        })
    })


    // $('.summernote').summernote({
    //     height: 150,   //set editable area's height
    //     // codemirror: { // codemirror options
    //     //     theme: 'monokai',
    //     //     mode: "text/x-java",
    //     //     lineNumbers: true,
    //     //     matchBrackets: true
    //     // },
    //     hint: {
    //         words: ['public', 'static', 'int', 'boolean'],
    //         match: /\b(\w{1,})$/,
    //         search: function (keyword, callback) {
    //             callback($.grep(this.words, function (item) {
    //                 return item.indexOf(keyword) === 0;
    //             }));
    //         },
    //         fontsize: "18px",
    //         prettifyHtml: false,
    //         toolbar: [
    //             ['highlight', ['highlight']],
    //         ],
    //         language: 'java'
    //     }
    // });
});

