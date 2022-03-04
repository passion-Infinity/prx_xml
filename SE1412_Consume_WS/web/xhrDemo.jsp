<%-- 
    Document   : xhrDemo
    Created on : Mar 4, 2022, 7:12:12 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>XHR Demo</title>
    </head>
    <body>
        <h1>Search With JS</h1>
        <form name="myForm">
            Title: <input type="text"  name="txtSearch"/>
            <input type="button" value="Search" onclick="traversalDOMTree('dataTable')"/>
        </form>
        <table border="1" id="dataTable">
            <thead>
                <tr>
                    <th>No.</th>
                    <th>Title</th>
                    <th>Link</th>
                    <th>Description</th>
                    <th>Pub Date</th>
                </tr>
            </thead>
        </table>
        <script type="text/javascript">
            var count = 0;
            var cells = [];
            var xmlDOM = null;
            var resultDOM = null;
            var xmlHttp;
            var tmpTableName;
            function addRow(tableId, cells) {
                var tableElem = document.getElementById(tableId);
                var newRow = tableElem.insertRow(tableElem.rows.length);
                var newCell;
                for (var i = 0; i < cells.length; i++) {
                    newCell = newRow.insertCell(newRow.cells.length);
                    newCell.innerHTML = cells[i];
                }
                return newRow;
            }

            function deleteRow(tableId, rowNumber) {
                var tableElem = document.getElementById(tableId);
                if (rowNumber > 0 && rowNumber < tableElem.rows.length) {
                    tableElem.deleteRow(rowNumber);
                } else {
                    alert("Failed");
                }
            }

            function getXmlHttpObject() {
                var xmlHttp = null;
                try {
                    xmlHttp = new XMLHttpRequest();
                } catch (e) {
                    try {
                        xmlHttp = new ActiveXObject("Msxml12.XMLHTTP");
                    } catch (e) {
                        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                }
                return xmlHttp;
            }

            function traversalDOMTree(tableName) {
                var tableElem = document.getElementById(tableName);
                var i = 1;
                while (i < tableElem.rows.length) {
                    deleteRow(tableName, i);
                }
                count = 0;
                tmpTableName = tableName;
                resultDOM = null;
                searchNode(xmlDOM, myForm.txtSearch.value);
                if (resultDOM === null) {
                    searchDB();
                }
            }

            function searchDB() {
                xmlHttp = getXmlHttpObject();
                if (xmlHttp === null) {
                    alert("Your browser does not support AJAX");
                    return;
                }
                var url = "SearchController?txtSearch=" + myForm.txtSearch.value;
                xmlHttp.onreadystatechange = handleStateChange;
                xmlHttp.open("POST", url, true);
                xmlHttp.send(null);
            }

            function handleStateChange() {
                if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
                    var tmp = xmlHttp.responseText;
                    if (tmp !== "") {
                        var parser = new DOMParser();
                        alert("Data from server " + tmp);
                        xmlDOM = parser.parseFromString(tmp, "application/xml");
                        searchNode(xmlDOM, myForm.txtSearch.value);
                    }
                }
            }

            function searchNode(node, strSearch) {
                if (node === null)
                    return;

                if (node.tagName === "description") {
                    var title = node.nextSibling.nextSibling.nextSibling.nextSibling;
                    if (title.firstChild.nodeValue.indexOf(strSearch, 0) > -1) {
                        var id = node.nextSibling;
                        var link = id.nextSibling;
                        var pubDate = link.nextSibling;
                        count++;
                        cells[0] = count;
                        cells[1] = title.firstChild.nodeValue;
                        cells[2] = link.firstChild.nodeValue;
                        cells[3] = node.firstChild.nodeValue;
                        cells[4] = pubDate.firstChild.nodeValue;
                        resultDOM += "<newsDTO>";
                        resultDOM += "<description>" + cells[3] + "</description>";
                        resultDOM += "<id>" + id.firstChild.nodeValue + "</id>";
                        resultDOM += "<link>" + cells[2] + "</link>";
                        resultDOM += "<pubDate>" + cells[4] + "</pubDate>";
                        resultDOM += "<title>" + cells[1] + "</title>";
                        resultDOM += "</newsDTO>";
                        addRow(tmpTableName, cells);
                    }
                }
                
                var children = node.childNodes;
                for(var i = 0; i < children.length; i++) {
                    searchNode(children[i], strSearch);
                }
            }
        </script>
    </body>
</html>
