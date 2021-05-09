/*
 Template Name: Zegva - Responsive Bootstrap 4 Admin Dashboard
 Author: Themesdesign
 Website: www.themesdesign.in
 File: Datatable js
 */

$(document).ready(function() {
    let dataTable = $('#datatable').DataTable({
        language: {
            "sProcessing": "处理中...",
            "sLengthMenu": "显示 _MENU_ 项结果",
            "sZeroRecords": "没有匹配结果",
            "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
            "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
            "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
            "sInfoPostFix": "",
            "sSearch": "搜索:",
            "sUrl": "",
            "sEmptyTable": "表中数据为空",
            "sLoadingRecords": "载入中...",
            "sInfoThousands": ",",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上页",
                "sNext": "下页",
                "sLast": "末页"
            },
            "oAria": {
                "sSortAscending": ": 以升序排列此列",
                "sSortDescending": ": 以降序排列此列"
            }
        }
    });

    // $('#datatable').on('click', 'tbody tr button', function () {
    //     $.ajax({
    //         url: baseUrl + '/question-delete',
    //         type: 'post',
    //         data: params,
    //         contentType: 'application/json',
    //         dataType: "json",
    //         success: function (res) {
    //             swal("删除成功！","提示", "success");
    //         },
    //         error: function (res) {
    //             let data = jQuery.parseJSON(res.responseText);
    //             swal(data.message, "删除失败", "error");
    //         }
    //     })
    // });

    $('#datatable').on('click', 'tbody tr button', function () {
        let data = dataTable.row($(this).parent()).data();
        let questionOrder = data[0];
        swal({
            title: '提示',
            text: "你确定要删除吗",
            type: 'warning',
            showCancelButton: true,
            confirmButtonClass: 'btn btn-success',
            cancelButtonClass: 'btn btn-danger ml-2',
            confirmButtonText: '确定',
            cancelButtonText: '取消'
        }).then(function () {
            $.ajax({
                url: baseUrl + '/question-delete/' + questionOrder,
                type: 'post',
                contentType: 'application/json',
                dataType: "json",
                success: function (res) {
                    swal(
                        '已删除!',
                        '该题目已成功删除',
                        'success'
                    ).then(function () {
                        window.location.reload();
                    })
                },
                error: function (res) {
                    let data = jQuery.parseJSON(res.responseText);
                    swal(data.message, "删除失败", "error").then(function () {
                        window.location.reload()
                    });
                }
            })
        })
    });

    $('#question-title').click(function () {
        let data = dataTable.row($(this).parent()).data();
        let questionOrder = data[0];
        $(location).attr('href', baseUrl + '/question/' + questionOrder);
    })

    //Buttons examples
    var table = $('#datatable-buttons').DataTable({
        lengthChange: false,
        buttons: ['copy', 'excel', 'pdf', 'colvis']
    });

    table.buttons().container()
        .appendTo('#datatable-buttons_wrapper .col-md-6:eq(0)');
} );
