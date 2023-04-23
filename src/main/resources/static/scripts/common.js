const toggle = document.querySelector(".menu-toggle");
const menu = document.querySelector(".menu");
const selectedGenre= document.querySelector("#genre-name");
const selectedTitle = document.querySelector("#movie-title");
const selectedDelMovie = document.querySelector("#movie-del-title");
let previousSelectedMovieForm;
let previousSelectedGenreForm;
let previousSelectedDelMovieForm;

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


if (selectedTitle != null) {
    selectedTitle.addEventListener("change", (e) => displayMovieForm());
}
if (selectedGenre != null) {
    selectedGenre.addEventListener("change", () => displayGenreForm());
}

if (selectedDelMovie != null) {
    selectedDelMovie.addEventListener("change", () => displayMoviePreview());
}

function displayMovieForm() {
    let movie_index = selectedTitle.value;
    let movie_form = document.getElementById('movie-option-' + movie_index);
    if (previousSelectedMovieForm != null) {
        previousSelectedMovieForm.classList.add('invisible');
    }
    movie_form.classList.remove('invisible');
    previousSelectedMovieForm = movie_form;
}
function displayGenreForm() {
    let genre_index = selectedGenre.value;
    let genre_form = document.getElementById('genre-option-' + genre_index);
    if (previousSelectedGenreForm != null) {
        previousSelectedGenreForm.classList.add('invisible');
    }
    genre_form.classList.remove('invisible');
    previousSelectedGenreForm = genre_form;
}

function displayMoviePreview() {
    let movieToDeleteIndex = selectedDelMovie.value - 1;
    let movieToDeletePreview = document.getElementById('movie-del-option-' + movieToDeleteIndex);
    if (previousSelectedDelMovieForm != null) {
        previousSelectedDelMovieForm.classList.add('invisible');
    }
    movieToDeletePreview.classList.remove('invisible');
    previousSelectedDelMovieForm = movieToDeletePreview;
}
