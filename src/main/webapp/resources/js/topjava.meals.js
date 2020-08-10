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
    var url = new URL(window.location.href);

    str = url.origin + url.pathname;

    var newUrl = str.replace(/\/filter[/]*$/i, "");

    var params = $('#filterForm input').filter(function(){
        return $(this).val();
    }).serialize();;

    if (params) { params = "/filter?" + params; }
    // window.location.href = url.origin + url.pathname + params;
    history.pushState(null, '', newUrl + params);
    updateTable();
}

function clearFilter() {
    $('#filterForm').find(":input").val("");
    filter();
}