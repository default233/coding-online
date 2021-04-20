jQuery(document).ready(function() {
    let marked1 = marked($("#description").val());
    $('#question').append(marked1);

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


    $('#theme-dropdown').on('click', 'a', function() {
        let theme = $(this)[0].innerText;
        coder.setOption('theme', theme);
        location.hash = '#' + theme
    })

    $('#language-dropdown').on('click', 'a', function() {
        let mode = $(this)[0].innerText;
        switch (mode) {
            case 'c++' : mode = 'text/x-c++src'; break;
            case 'c' : mode = 'text/x-c'; break;
            case 'java' : mode = 'text/x-java'; break;
            case 'python' :mode = 'python'; break
        }
        coder.setOption('mode', mode);
        location.hash = '#' + mode
    })
    // let $dropdown = $('#dropdown-language-button');
    // $dropdown.click(function () {
    //     if ($dropdown.hasClass("show")) {
    //         $dropdown.removeClass("show");
    //         $dropdown.after().removeClass("show");
    //     } else {
    //         $dropdown.addClass("show");
    //         $dropdown.after().addClass("show");
    //     }
    // })
    // let $theme = $('#dropdown-theme-button');
    // $theme.click(function () {
    //     if ($theme.hasClass("show")) {
    //         $theme.removeClass("show");
    //         $theme.after().removeClass("show");
    //     } else {
    //         $theme.addClass("show");
    //         $theme.after().addClass("show");
    //     }
    // })

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

