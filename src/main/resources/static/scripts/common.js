const toggle = document.querySelector(".menu-toggle");
const menu = document.querySelector(".menu");
const selectedGenre= document.querySelector("#genre-name");
const selectedTitle = document.querySelector("#movie-title");
const selectedDelMovie = document.querySelector("#movie-del-title");
const selectedDelGenre = document.querySelector("#genre-del-name");
const posterInput = document.querySelector(".custom-file-upload input");
const userTabDropdownBtn = document.querySelector(".user-tab .avatar");
let userTabDropdown = document.querySelector('.user-tab ul');
let previousSelectedMovieForm;
let previousSelectedGenreForm;
let previousSelectedDelMovieForm;
let previousSelectedDelGenreForm;

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
    selectedTitle.addEventListener("change", () => displayMovieForm());
}
if (selectedGenre != null) {
    selectedGenre.addEventListener("change", () => displayGenreForm());
}

if (selectedDelMovie != null) {
    selectedDelMovie.addEventListener("change", () => displayMoviePreview());
}

if (selectedDelGenre != null) {
    selectedDelGenre.addEventListener("change", () => displayGenrePreview());
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

function displayGenrePreview() {
    let genreToDeleteIndex = selectedDelGenre.value - 1;
    let genreToDeletePreview = document.getElementById('genre-del-option-' + genreToDeleteIndex);
    if (previousSelectedDelGenreForm != null) {
        previousSelectedDelGenreForm.classList.add('invisible');
    }
    genreToDeletePreview.classList.remove('invisible');
    previousSelectedDelGenreForm = genreToDeletePreview;
}

document.getElementById("year").innerHTML = new Date().getFullYear().toString();

function getCurrentYear() {
   return new Date().getFullYear().toString();
}

if (posterInput != null) {
    posterInput.onchange = () => {
        const [file] = posterInput.files;
        if (file) {
            let image = document.querySelector(".poster-preview img");
            image.src = URL.createObjectURL(file);
        }
    };
}


if (userTabDropdownBtn != null) {
    window.onclick = (e) => {
        let avatarIsClicked = e.target === userTabDropdownBtn;
        let popupIsVisible = userTabDropdown.style.display === 'block';
        let isClickedOutsidePopup = !userTabDropdown.contains(e.target);

        if (window.innerWidth < 992) showPopup();
        else if (avatarIsClicked && popupIsVisible) hidePopup();
        else if (avatarIsClicked) showPopup();
        else if (isClickedOutsidePopup) hidePopup();
    };
}

function hidePopup() {
    userTabDropdown.style.display = 'none';
}

function showPopup() {
    userTabDropdown.style.display = 'block';
}