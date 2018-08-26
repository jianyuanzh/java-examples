// $.ajax({
//     url: "/demo/err/getAjaxerror",
//     type: "GET",
//     async: false,
//     success: function (data) {
//         debugger;
//         if (data.code == 200) {
//             alert("success")
//         }
//         else {
//             alert("Exception: " + data.errMsg)
//         }
//     },
//     error: function (response, ajaxOptions, thrownError) {
//         debugger;
//         alert("error")
//     }
// });


$.ajax({
    url: "/demo/err/getAjaxerror",
    type: "GET",
    async: false,
    success: function(data) {
        debugger;
        if(data.code == 200) {
            alert("success");
        } else {
            alert("发生异常：" + data.errMsg);
        }
    },
    error: function (response, ajaxOptions, thrownError) {
        debugger;
        alert("error");
    }
});