$(document).ready(function () {
    var myTable = $('#myTable');
    var host = window.location.origin;
    var url = host+ "/ictlab/api/containers/";
    var nodes = [];

    getNumberOfNodes();

    var myTableDataTable = myTable.DataTable({
        "ajax": {
            "url": url,
            "dataSrc": ""
        },
        "columns": [
            {"data": "id"},
            {"data": "name"},
            {"data": "creationdate"},
            {"data": "state"},
            {
                "render": function (data, type, full, meta) {
                    return '<span class="glyphicon glyphicon-dy glyphicon-play start" data-command="start" data-toggle="tooltip" title="Start a container"></span><span class="glyphicon glyphicon-dy glyphicon-off" data-toggle="tooltip"  data-command="stop" title="Stop a container"></span><span class="glyphicon glyphicon-dy glyphicon-repeat" data-command="restart" data-toggle="tooltip" title="Restart a container"></span><span class="glyphicon glyphicon-dy glyphicon-export" data-command="move" data-toggle="tooltip" title="Move a container"></span><span class="glyphicon glyphicon-dy glyphicon-duplicate scale" data-command="scale" data-toggle="scale" title="Scale a container"></span>';
                }
            }
        ],
        "order": [[0, "asc"]],
        "initComplete": function (settings, json) {
            $('.loadDiv').hide();
        }
    });

    $( "#myTable tbody" ).on( "click","span" ,function() {
        var command = $(this).attr('data-command');

        if(command == "move"){
            move($(this));
        }else {
            startRequest($(this), command);
        }
    });

    function move(obj) {
        var rowData = myTableDataTable.row(obj.parents('tr')).data();
        var id = rowData.id;
        var url = host+ "/ictlab/api/containers/" + id + "/move/";

        //Alle nodes staan in de nodes var.
        //TODO: Alle nodes tonen en 1 selecteren.
        //TODO: Node ophalen uit select
        var node = "Node1";

        $.ajax({
            type: "POST",
            dataType : "json",
            url: url,
            data: {
                node : node
            }
        });
    }

    function startRequest(obj, command){
        var parentTableRow = obj.parents('tr');
        var rowData = myTableDataTable.row(parentTableRow).data();
        var id = rowData.id;
        var url = host+ "/ictlab/api/containers/" + id + "/"+ command + "/";

        //Command can be start/stop/move/restart
        console.log("command: " + command + " url: " + url);

        $.ajax({
            type: "GET",
            dataType: "json",
            url: url,
            data: {
                id: id
            },
            statusCode: {
                201: function () {
                    parentTableRow.css("background-color", "green");
                },
                503: function () {
                    parentTableRow.css("background-color", "red");
                }

            }
        });

        reloadDataTable(3000);
    }

    function reloadDataTable(ms) {
        setTimeout(function () {
            myTableDataTable.ajax.reload();
        }, ms);
    }

    $('#newContainer').click(function () {
        $('#newContainerModal').modal('show');
        setNumberOfNodes();
    });

    $('#startNewContainer').click(function () {
        var containerName = $('#newContainerName').val();
        var node = $('#startingNode option:selected').text();
        var baseImage = $('#newContainerBaseImage').val();
        var hostPort = $('#hostPort').val();
        var containerPort = $('#containerPort').val();
        var url = host + "/ictlab/api/containers/";

        $.ajax({
            type: "POST",
            dataType: "json",
            url: url,
            data : {
                containerName: containerName,
                node: node,
                baseImage: baseImage,
                hostPort: hostPort,
                containerPort: containerPort
            },
            success: function() {
                alert("Create container request sent!")
            }
        });

    });

    function setNumberOfNodes() {
        $.each(nodes, function( index, value ) {
            $('#startingNode').append($('<option>').text(value).attr('value', value));
        });
    }
    
    function getNumberOfNodes() {
        var url = host + "/ictlab/api/nodes/";

        $.ajax({
            url: url,
            type:'GET',
            dataType: 'json',
            success: function(json) {
                $.each(json, function(i, node) {
                    nodes.push(node.name);
                });
            }
        });
    }

});
