/*
 Template Name: Zegva - Responsive Bootstrap 4 Admin Dashboard
 Author: Themesdesign
 Website: www.themesdesign.in
 File: Xeditable js
 */

$(function () {


    //modify buttons style
    $.fn.editableform.buttons =
        '<button type="submit" class="btn btn-success editable-submit btn-sm waves-effect waves-light"><i class="mdi mdi-check"></i></button>' +
        '<button type="button" class="btn btn-danger editable-cancel btn-sm waves-effect waves-light"><i class="mdi mdi-close"></i></button>';
    //inline
    // $.fn.datepicker.dates['zh-CN'] = {
    //     days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
    //     daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六"],
    //     daysMin: ["日", "一", "二", "三", "四", "五", "六"],
    //     months: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
    //     // months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
    //     monthsShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
    //     today: "今天",
    //     monthsTitle: "选择月份",
    //     clear: "清除",
    //     format: "yyyy-mm-dd",
    //     titleFormat: "yyyy年mm月",
    //     weekStart: 1
    // };

    $('#username').editable({
        value: currentUser.username,
        ajaxOptions: {
            type: 'post',
            contentType: 'application/json'
        },
        params: function(params) {
            return JSON.stringify(params);
        },
        validate: function (value) {
            if ($.trim(value) == '') return '用户名不能为空';
        },
        url: baseUrl + "/username",
        error: function(response, newValue) {
            let data = jQuery.parseJSON(response.responseText);
            return data.message;
        },
        mode: 'inline',
        inputclass: 'form-control-sm',
    });

    $('#sex').editable({
        prepend: currentUser.sex,
        mode: 'inline',
        inputclass: 'form-control-sm',
        source: [
            // {value: '男', text: '男'},
            currentUser.sex == '男'?{value: '女', text: '女'} : {value: '男', text: '男'}
        ],
        display: function (value, sourceData) {
            var colors = {"": "#98a6ad", 1: "#5fbeaa", 2: "#5d9cec"},
                elem = $.grep(sourceData, function (o) {
                    return o.value == value;
                });

            if (elem.length) {
                $(this).text(elem[0].text).css("color", colors[value]);
            } else {
                $(this).empty();
            }
        },
        ajaxOptions: {
            type: 'post',
            contentType: 'application/json'
        },
        params: function(params) {
            return JSON.stringify(params);
        },
        url: baseUrl + "/sex",
    });

    $('#email').editable({
        value: currentUser.email,
        ajaxOptions: {
            type: 'post',
            contentType: 'application/json'
        },
        params: function(params) {
            return JSON.stringify(params);
        },
        validate: function (value) {
            if ($.trim(value) == '') return '邮箱不能为空';
        },
        url: baseUrl + "/email",
        mode: 'inline',
        error: function(response, newValue) {
            let data = jQuery.parseJSON(response.responseText);
            return data.message;
        },
        inputclass: 'form-control-sm',
    });

    $('#img-header').attr('src', "show-img?filename=" + currentUser.img);
    $('#head-a').click(function () {
        $('#head-modal').modal("show");
    });


    $('#birthday').editable({
        value: currentUser.birthday,
        mode: 'inline',
        type: 'data',
        datepicker: {
            language: 'zh-CN',//中文支持
        },
        ajaxOptions: {
            type: 'post',
            contentType: 'application/json'
        },
        params: function(params) {
            return JSON.stringify(params);
        },
        url: baseUrl + "/birthday",
        inputclass: 'form-control-sm datepicker'
    });

});
