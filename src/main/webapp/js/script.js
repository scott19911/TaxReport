$(document).ready(
$(function() {
  var oTable = $('#sortable').DataTable({
    "oLanguage": {
      "sSearch": "Filter Date"
    },
    "iDisplayLength": -1,
    "sPaginationType": "full_numbers",

  });

$("#datepicker_from").datepicker({
  dateFormat: 'dd.mm.yy',
  showOn: "button",
  buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
  buttonImageOnly: false,
  "onSelect": function(date) {
    var dateArr = date.split(".");
    var newDate = dateArr[2] + '.' + dateArr[1] + '.' + dateArr[0];
    maxDateFilter = new Date(newDate).getTime();
    oTable.fnDraw();
  }
}).keyup(function() {
  minDateFilter = new Date(this.value).getTime();
  oTable.fnDraw();
});

$("#datepicker_to").datepicker({
  dateFormat: 'dd.mm.yy',
  showOn: "button",
  buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
  buttonImageOnly: false,
  "onSelect": function(date) {
  var dateArr = date.split(".");
  var newDate = dateArr[2] + '.' + dateArr[1] + '.' + dateArr[0];

    maxDateFilter = new Date(newDate).getTime();
    oTable.fnDraw();
  }
}).keyup(function() {
  maxDateFilter = new Date(this.value).getTime();
  oTable.fnDraw();
});

}));

// Date range filter
minDateFilter = "";
maxDateFilter = "";

$.fn.dataTableExt.afnFiltering.push(
    function(oSettings, aData, iDataIndex) {
      if (typeof aData._date == 'undefined') {
        var dateArr = aData[2].split(".");
        var newDate = dateArr[2] + '.' + dateArr[1] + '.' + dateArr[0];
        aData._date = new Date(newDate).getTime();
      }

      if (minDateFilter && !isNaN(minDateFilter)) {
        if (aData._date < minDateFilter) {

          return false;
        }
      }


      if (maxDateFilter && !isNaN(maxDateFilter)) {
        if (aData._date > maxDateFilter) {
         
          return false;
        }
      }

      return true;
    }
);