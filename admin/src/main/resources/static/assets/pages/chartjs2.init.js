/*
 Template Name: Zegva - Responsive Bootstrap 4 Admin Dashboard
 Author: Themesdesign
 Website: www.themesdesign.in
 File: Chart js
 */
var chartDate = {
    "pieData": [],
    "radarData": []
};

!function($) {
    "use strict";

    var ChartJs = function() {};

    ChartJs.prototype.respChart = function(selector,type,data, options) {
        // get selector by context
        var ctx = selector.get(0).getContext("2d");
        // pointing parent container to make chart js inherit its width
        var container = $(selector).parent();

        // enable resizing matter
        $(window).resize( generateChart );

        // this function produce the responsive Chart JS
        function generateChart(){
            // make chart width fit with its container
            var ww = selector.attr('width', $(container).width() );
            switch(type){
                case 'Line':
                    new Chart(ctx, {type: 'line', data: data, options: options});
                    break;
                case 'Pie':
                    new Chart(ctx, {type: 'pie', data: data, options: options});
                    break;
                case 'Bar':
                    new Chart(ctx, {type: 'bar', data: data, options: options});
                    break;
                case 'Radar':
                    new Chart(ctx, {type: 'radar', data: data, options: options});
                    break;
            }
            // Initiate new chart or Redraw

        };
        // run function - render chart at first load
        generateChart();
    },
    //init
    ChartJs.prototype.init = function(chartData) {

        //Pie chart
        var pieChart = {
            labels: [
                "通过次数",
                "未通过次数"
            ],
            datasets: [
                {
                    data: chartData.pieData,
                    backgroundColor: [
                        "#0e86e7",
                        "#f0f4f7"
                    ],
                    hoverBackgroundColor: [
                        "#0e86e7",
                        "#f0f4f7"
                    ],
                    hoverBorderColor: "#fff"
                }]
        };
        this.respChart($("#pie"),'Pie',pieChart);


        //radar chart
        var radarChart = {
            labels: ["错误答案", "超出内存限制", "超出运行限制", "运行时错误", "编译错误"],
            datasets: [
                {
                    label: "错误类型",
                    backgroundColor: "rgba(14, 134, 231, 0.2)",
                    borderColor: "#0e86e7",
                    pointBackgroundColor: "#0e86e7",
                    pointBorderColor: "#fff",
                    pointHoverBackgroundColor: "#fff",
                    pointHoverBorderColor: "#0e86e7",
                    data: chartDate.radarData
                }
            ]
        };
        this.respChart($("#radar"),'Radar',radarChart);

    },
    $.ChartJs = new ChartJs, $.ChartJs.Constructor = ChartJs

}(window.jQuery),

//initializing
$(document).ready(function () {
    var url = window.location.href;
    let userId = url.substring(url.lastIndexOf('?'));
    // 获取饼图题目提交次数数据
    $.ajax({
        url: baseUrl + '/user-chart-pie'+userId,
        type: 'post',
        contentType: 'application/json',
        async: false,
        success: function (res) {
            let pieData = JSON.parse(res)
            chartDate.pieData = pieData;
            $("#submit-num").text(pieData[0] + pieData[1]);
            $("#success-num").text(pieData[0]);
            $("#failure-num").text(pieData[1]);
        }
    })
    // 获取雷达图数据
    $.ajax({
        url: baseUrl + '/user-chart-radar'+userId,
        type: 'post',
        contentType: 'application/json',
        async: false,
        success: function (res) {
            let resData = JSON.parse(res);
            console.log(resData)
            let radarData = resData.doubles;
            chartDate.radarData = radarData;
            let errorData = resData.errorArr;
            $("#compile-error").text(errorData[4]);
            $("#runtime-error").text(errorData[3]);
            $("#error-answer").text(errorData[0]);
        }
    })

    "use strict";
    $.ChartJs.init(chartDate)
})

