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

        let pathname = window.location.pathname;
        let questionOrder = pathname.substring(pathname.lastIndexOf('/')+1, pathname.lastIndexOf('#') < 0 ? pathname.length : pathname.lastIndexOf('#'));


        let codeSource = coder.getValue();
        let mode1 = coder.getOption('mode');
        switch (mode1) {
            case 'text/x-c' : mode1 = 1; break;
            case 'text/x-c++src' : mode1 = 2; break;
            case 'text/x-java' : mode1 = 3; break;
            case 'python' :mode1 = 4; break
        };

        let params = {
            // 'className': codeSource.substring(codeSource.indexOf("class") + 5,codeSource.indexOf("{")).trim(),
            'questionOrder': questionOrder,
            'source': codeSource,
            'compilerId': mode1,

        };
        console.log(JSON.stringify(params));
        $.ajax({
            url: baseUrl + '/code-submit',
            type: 'post',
            data: JSON.stringify(params),
            contentType: 'application/json',
            dataType: "json",
            success: function (res) {
                let errorMessage =  res.errorMessage;
                let status = res.status;
                let timeUsed;
                let memoryUsed;
                if (status == 0) {
                    timeUsed = res.timeUsed;
                    memoryUsed = res.memoryUsed;
                }

                switch (status) {
                    case 0 : status="通过！";break;
                    case 1 : status="错误答案！";break;
                    case 2 : status="输出格式错误";break;
                    case 3 : status="超出了题目的内存限制";break;
                    case 4 : status="超出了题目的时间限制";break;
                    case 5 : status="运行时错误";break;
                    case 6 : status="编译错误";break;
                    case 7 : status="使用了不安全的函数";break;
                }

                $('#code-div').nextAll().remove()
                $('#code-div').after("<div class=\"row\">\n" +
                    "                                <div class=\"col-md-12\">\n" +
                    "<div class=\"row col-10\">\n" +
                    "                                            <div class=\"card card-body col-6\">\n" +
                    "                                                <h4 class=\"card-title font-16 mt-0 font-weight-bolder\">运行结果: "+status+"</h4>\n" +
                    "                                                <div class=\"card-text\" id='card-error'>"+""+"" +
                    "                                                </div>\n" +
                    // "                                                <p class=\"card-text\">\n 消耗内存：0kb" +
                    //                     // "                                                </p>\n" +
                    "                                            </div>\n" +

                    "                                        </div>"+
                    "                                </div>\n" +
                    "                            </div>")
                if (res.status == 0) {
                    $('#card-error').append(
                        "<p class='card-text'> 内存消耗："+ memoryUsed +" kb</p>" +
                        "<p class='card-text'> 时间消耗："+ timeUsed +" ms </p>"
                    )
                } else {
                    $('#card-error').append("\"<p class='card-text' style='white-space:pre'> "+ errorMessage +"</p>\"")
                }
            },
            error: function (res) {
                alert("error!!!");
            }

        })
    })


    $('#theme-dropdown').on('click', 'a', function() {
        let theme = $(this)[0].innerText;
        $('#dropdown-theme-button').text(theme);
        coder.setOption('theme', theme);
        location.hash = '#' + theme
    })

    $('#language-dropdown').on('click', 'a', function() {
        let mode = $(this)[0].innerText;
        $('#dropdown-language-button').text(mode);
        switch (mode) {
            case 'c' : mode = 'text/x-c'; break;
            case 'c++' : mode = 'text/x-c++src'; break;
            case 'java' : mode = 'text/x-java'; break;
            case 'python' :mode = 'python'; break
        }
        coder.setOption('mode', mode);
        location.hash = '#' + $(this)[0].innerText
    })

});

