function selectPictureOfHero() {
    if (document.getElementById('gender_man').checked) {
        document.getElementById('man').style.visibility = 'visible';
        // document.getElementById('man').style.display = 'table-column';
        document.getElementById('man').style.display = 'flex';
        document.getElementById('woman').style.visibility = 'hidden';
        document.getElementById('woman').style.display = 'none';
    } else if (document.getElementById('gender_woman').checked) {
        document.getElementById('woman').style.visibility = 'visible';
        // document.getElementById('woman').style.display = 'table-column';
        document.getElementById('woman').style.display = 'flex';
        document.getElementById('man').style.visibility = 'hidden';
        document.getElementById('man').style.display = 'none';
    }
}