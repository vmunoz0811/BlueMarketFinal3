const grid = new Muuri('.grid', {
	layout: {
		rounding: false
	}
});
window.addEventListener('load', () => {
	grid.refreshItems().layout();
	document.getElementById('grid').classList.add('charged-images');

    //filtrar por categoria//
	const links = document.querySelectorAll('#categories a');
	links.forEach((element) => {
		element.addEventListener('click', (event) => {
			event.preventDefault();
			links.forEach((enlace) => enlace.classList.remove('active'));
			event.target.classList.add('active');

			const category = event.target.innerHTML.toLowerCase();
			category === 'todos' ? grid.filter('[data-category]') : grid.filter(`[data-category="${category}"]`);
		});
	});
    //filtar por busqueda//
    document.querySelector('#search').addEventListener('input', (event)=>{
        const search = event.target.value;
        grid.filter((item) => item.getElement().dataset.tags.includes(search));
    });
    //filtar imagenes//
    const overlay=document.getElementById('overlay');
    document.querySelectorAll('.grid .item img').forEach((element)=>{
              
        element.addEventListener('click', ()=>{
			const path = element.getAttribute('src');
			const description = element.parentNode.parentNode.dataset.description;
			const price = element.parentNode.parentNode.dataset.price;
			const autor = element.parentNode.parentNode.dataset.autor;


			overlay.classList.add('active');
			document.querySelector('#overlay img').src = path;
			document.querySelector('#overlay .description').innerHTML = price +"<br>"+ description +"<br>" + autor;
        }); 
    });
    //boton cerrar//
    document.querySelector('#btn-close-popup').addEventListener('click', () => {
		overlay.classList.remove('active');
		overlay.classList.remove('onclick');
	});
    //overlay//
    overlay.addEventListener('click', (event) => {
		event.target.id === 'overlay' ? overlay.classList.remove('active') : '';
		event.target.id === 'overlay' ? overlay.classList.remove('onclick') : '';
	});
	//boton like//
	document.querySelector('#btn-like').addEventListener('click', () => {
		overlay.classList.add('onclick');
	});
});
