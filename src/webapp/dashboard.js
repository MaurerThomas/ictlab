/**
 * @version 1.0.0
 * @since 10-06-2016
 */

/**
 * Starting URL for all requests, based on the website the user opens.
 * @example {string} "http://192.168.1.199:8080"
 * @type {string}
 */
const host = window.location.origin + "/ictlab/api" ;

/**
 * The string[] where all alive node IP-addresses will be saved.
 * @type {Array}
 */
var nodes = [];

/**
 * This var is used to save the instanced DataTable object in. Is used in multiple functions.
 */
var myTableDataTable;

$(document).ready(function () {
    var url = host + "/containers/";

    getNumberOfNodes();

    /**
     * Initialize a new DataTable object on the given HTML table
     * @return {Object} appends the action buttons to the HTML TableRow after the JSON is loaded
     */
    myTableDataTable = $('#myTable').DataTable({
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
                "render": function () {
                    return '<span class="glyphicon glyphicon-play" data-command="start" data-toggle="tooltip" title="Start a container"></span><span class="glyphicon glyphicon-off" data-toggle="tooltip"  data-command="stop" title="Stop a container"></span><span class="glyphicon glyphicon-repeat" data-command="restart" data-toggle="tooltip" title="Restart a container"></span><span class="glyphicon glyphicon-export" data-command="move" data-toggle="tooltip" title="Move a container"></span><span class="glyphicon glyphicon-duplicate" data-command="scale" data-toggle="scale" title="Scale a container"></span>';
                }
            }
        ],
        "order": [[0, "asc"]],
        "initComplete": function () {
            $('.loadDiv').hide();
        }
    });

    /**
     * Filter which button has been clicked on a row to start the specific method.
     */
    $( "#myTable tbody" ).on( "click","span" ,function() {
        var command = $(this).attr('data-command');

        if(command === "move"){
            move($(this));
        }else {
            startRequest($(this), command);
        }
    });

    /**
     * Show HTML modal where you can fill in details to create a new Container.
     * Set the number of nodes on this modal.
     */
    $('#newContainer').click(function () {
        $('#newContainerModal').modal('show');
        setNumberOfNodes();
    });

    /**
     * Start a new container create request with filled in details
     */
    $('#startNewContainer').click(function () {
        var containerName = $('#newContainerName').val();
        var node = $('#startingNode option:selected').text();
        var baseImage = $('#newContainerBaseImage').val();
        var hostPort = $('#hostPort').val();
        var containerPort = $('#containerPort').val();
        var url = host + "/containers/";

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

});

/**
 * Appends all items in the string[] nodes to a HTML select as option.
 */
function setNumberOfNodes() {
    $.each(nodes, function( index, value ) {
        $('#startingNode').append($('<option>').text(value).attr('value', value));
    });
}

/**
 * Gets all the possible Nodes and places these in the string[] nodes
 */
function getNumberOfNodes() {
    var url = host + "/nodes/";

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

/**
 * Start the JSON request to move a container
 * @param {Object} currentObject
 */
function move(currentObject) {
    var rowData = myTableDataTable.row(currentObject.parents('tr')).data();
    var id = rowData.id;
    var url = host+ "/containers/" + id + "/move/";

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

/**
 * Start a specific JSON request. Can be start/stop/restart/scale
 * @param {Object} currentObject
 * @param {string} command
 *
 * @example
 * var currentObject = [HTML OBJECT] //A TableRow for example.
 * var command  = "start"
 * startRequest(currentobject, command)
 */
function startRequest(currentObject, command){
    var parentTableRow = currentObject.parents('tr');
    var rowData = myTableDataTable.row(parentTableRow).data();
    var id = rowData.id;
    var url = host+ "/containers/" + id + "/"+ command + "/";

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