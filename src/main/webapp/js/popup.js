var btnOpenPopUp =document.getElementById('btn-open-popup1'),
    overlay = document.getElementById('overlayPopup'), 
    popup = document.getElementById('popup'), 
    btnClosePopUp = document.getElementById('btn-close-popup1');
    
    btnOpenPopUp.addEventListener('click', function(){
        overlay.classList.add('active');
        popup.classList.add('active')
    });

    btnClosePopUp.addEventListener('click', function(){
        overlay.classList.remove('active');
        popup.classList.remove('active')
    });
