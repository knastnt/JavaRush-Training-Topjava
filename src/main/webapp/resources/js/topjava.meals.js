// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
});

function fillFields(form, id) {
    var tds = $("#datatable").find("tr[data-id =\"" + id + "\"]").find("td");
    form.find(":input[name=\"id\"]").val(id);
    form.find(":input[name=\"dateTime\"]").val($(tds)[0].innerText);
    form.find(":input[name=\"description\"]").val($(tds)[1].innerText);
    form.find(":input[name=\"calories\"]").val($(tds)[2].innerText);
}

function filter() {
    var re = "/filter",
        str = window.location.href;

    var url = new URL(str.replace(re, ""));

    var params = $('#filterForm :input[value!=""]').serialize();
    if (params) { params = "/filter?" + params; }
    window.location.href = url.origin + url.pathname + params;
    updateTable();
}