//<![CDATA[
function loadCurrentDate() {

    var today = new Date();
    var textToday = '' + today.getFullYear() + '-';

    if (today.getMonth() < 9) {     //getMonth() 0..11
        textToday += '0' + (today.getMonth() + 1) + '-';
    } else {
        textToday += (today.getMonth() + 1) + '-';
    }
    if(today.getDate() < 9) {
        textToday += '0' + today.getDate();
    } else {
        textToday += today.getDate();
    }

    document.getElementById('date').max = textToday;
}
//]]>
