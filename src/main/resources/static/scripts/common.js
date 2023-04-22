const toggle = document.querySelector(".menu-toggle");
const menu = document.querySelector(".menu");

function toggleMenu() {
    if (menu.classList.contains("expanded")) {
        menu.classList.remove("expanded");
        toggle.querySelector('a').innerHTML = '<i id="toggle-icon" class="far fa-plus-square"></i>';
    } else {
        menu.classList.add("expanded");
        toggle.querySelector('a').innerHTML = '<i id="toggle-icon" class="far fa-minus-square"></i>';
    }
}
toggle.addEventListener('click', toggleMenu, false);

let previousSelectedForm;
const selectedTitle = document.getElementById("movie-title");
selectedTitle.addEventListener("change", () => {

    let movie_index = selectedTitle.value;
    let movie_form = document.getElementById('movie-option-' + movie_index);

    if (previousSelectedForm != null) {
        previousSelectedForm.classList.add('invisible');
        previousSelectedForm = movie_form;
        movie_form.classList.remove('invisible');
    } else {
        previousSelectedForm = movie_form;
        movie_form.classList.remove('invisible');
    }
});
