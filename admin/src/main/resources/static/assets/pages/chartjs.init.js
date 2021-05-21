/*
 Template Name: Zegva - Responsive Bootstrap 4 Admin Dashboard
 Author: Themesdesign
 Website: www.themesdesign.in
 File: Chart js
 */
var chartDate = {
    "pieData": [],
    "lineData": [],
    "barData": [],
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
        //creating lineChart
        var lineChart = {
            labels: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
            datasets: [
                {
                    label: "题目热度",
                    fill: true,
                    lineTension: 0.5,
                    backgroundColor: "rgba(35, 203, 224, 0.2)",
                    borderColor: "#23cbe0",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    pointBorderColor: "#23cbe0",
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 1,
                    pointHoverRadius: 5,
                    pointHoverBackgroundColor: "#23cbe0",
                    pointHoverBorderColor: "#23cbe0",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: [30, 59, 80, 81, 56, 55, 40]
                }
            ]
        };

        var lineOpts = {
            scales: {
                yAxes: [{
                    ticks: {
                        max: 100,
                        min: 20,
                        stepSize: 10
                    }
                }]
            }
        };

        this.respChart($("#lineChart"),'Line',lineChart, lineOpts);


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


        //barchart
        var barChart = {
            labels: ["计算机班", "电子商务班", "信息管理班"],
            datasets: [
                {
                    label: "通过人数",
                    backgroundColor: "rgba(14, 134, 231, 0.4)",
                    borderColor: "#0e86e7",
                    borderWidth: 1,
                    hoverBackgroundColor: "rgba(14, 134, 231, 0.5)",
                    hoverBorderColor: "#0e86e7",
                    data: [10, 20, 40]
                },
                {
                    label: "提交人数",
                    backgroundColor: "rgba(168,250,110,0.4)",
                    borderColor: "#0e86e7",
                    borderWidth: 1,
                    hoverBackgroundColor: "rgba(168,250,110,0.5)",
                    hoverBorderColor: "#0e86e7",
                    data: [30, 35, 45]
                }
            ]
        };
        var barOpts = {
            scales: {
                yAxes: [{
                    ticks: {
                        max: 50,
                        min: 0,
                        stepSize: 10
                    }
                }]
            }
        };
        this.respChart($("#bar"),'Bar',barChart, barOpts);


        //radar chart
        var radarChart = {
            labels: ["答案正确", "内存限制满足", "时间限制满足", "运行正确", "编译正确"],
            datasets: [
                {
                    label: "正确性",
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
    let order = url.substring(url.lastIndexOf('?'));
    // 获取饼图题目提交次数数据
    $.ajax({
        url: baseUrl + '/question-chart-pie'+order,
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
        url: baseUrl + '/question-chart-radar'+order,
        type: 'post',
        contentType: 'application/json',
        async: false,
        success: function (res) {
            let resData = JSON.parse(res);
            let radarData = resData.doubles;
            chartDate.radarData = radarData;
            let errorData = resData.errorArr;
            $("#compile-error").text(errorData[4]);
            $("#runtime-error").text(errorData[3]);
            $("#error-answer").text(errorData[0]);
        }
    })
    // 获取条形图数据
    $.ajax({
        url: baseUrl + '/question-chart-bar'+order,
        type: 'post',
        contentType: 'application/json',
        async: false,
        success: function (res) {
            let resData = JSON.parse(res)
            let classInfo = resData.allClass;
            for (const classInfoElement of classInfo) {
                if (classInfoElement.className == "计算机班") {
                    $("#computer-num").text(classInfoElement.studentNums);
                } else if (classInfoElement.className == "电子商务班") {
                    $("#e-business-num").text(classInfoElement.studentNums);
                } else if (classInfoElement.className == "信息管理班") {
                    $("#info-management-num").text(classInfoElement.studentNums);
                }
            }
        }
    })

    "use strict";
    $.ChartJs.init(chartDate)
})

