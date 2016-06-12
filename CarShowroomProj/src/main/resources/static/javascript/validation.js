//<![CDATA[
function loadCurrentDate(where) {

    var today = new Date();
    var textToday = '' + today.getFullYear() + '-';

    if (today.getMonth() < 9) {     //getMonth() 0..11
        textToday += '0' + (today.getMonth() + 1) + '-';
    } else {
        textToday += (today.getMonth() + 1) + '-';
    }
    if (today.getDate() < 9) {
        textToday += '0' + today.getDate();
    } else {
        textToday += today.getDate();
    }

    if (where === 'min') {
        document.getElementById('date').min = textToday;
        alert(textToday);
    } else if(where === 'max') {
        document.getElementById('date').max = textToday;
    }
}

function loadStartDate() {
    var startDate = document.getElementById('date').value;
    alert(startDate);
    document.getElementById('date2').min = startDate;
}

function disableShowroom(){
    var value = document.menuForm.position.option[document.menuForm.position.selectedIndex].value;
    if(value == "Dyrektor"){
    document.write("JUHU lalala");
    }
}


function confirm_delete(node) {
    return confirm("Usunięcie rekordu spowoduje wstawienie null w wszystkich jego odwołaniach. Kliknij okej, by kontynuować");
}
//]]>
