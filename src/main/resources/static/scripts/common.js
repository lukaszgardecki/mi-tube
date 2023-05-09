const toggle = document.querySelector(".menu-toggle");
const menu = document.querySelector(".menu");
const selectedGenre= document.querySelector("#genre-name");
const selectedTitle = document.querySelector("#movie-title");
const selectedDelMovie = document.querySelector("#movie-del-title");
const selectedDelGenre = document.querySelector("#genre-del-name");
const posterInput = document.querySelector(".custom-file-upload input");
const avatarInput = document.querySelector(".manage-account .avatar input");
let initialLetter = document.querySelector(".manage-account .avatar span");
const userTabDropdownBtn = document.querySelector(".user-tab .avatar");
const manageAcc = document.querySelector(".manage-account");
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

function hideJumbotron() {
    document.querySelector(".jumbotron").style.display = 'none';
}

if (manageAcc != null) {
    hideJumbotron();

    let hoverElem = document.querySelector(".manage-account .avatar .background-icon");
    initialLetter.onmouseover = () => {
        hoverElem.style.display = 'flex';
    };
    initialLetter.onmouseout = () => {
        hoverElem.style.display = 'none';
    };
}

if (avatarInput != null) {
    avatarInput.onchange = () => {
        let avatarBackground = document.querySelector(".manage-account .avatar");

        const [file] = avatarInput.files;
        if (file) {
            let imagePath = URL.createObjectURL(file);
            avatarBackground.style.backgroundImage = 'url(' + imagePath + ')';
            initialLetter.innerHTML = '';
        }
    };
}

function hide(field) {
    field.style.display = 'none';
}

function show(field) {
    field.style.display = 'flex';
}

function isEmpty(field) {
    return field.value === '';
}

function isNotEmpty(field) {
    return field.value !== '';
}

if (manageAcc != null) {
    let newPasswordInput = document.querySelector(".manage-account #newPassword");
    let confirmPasswordInput = document.querySelector(".manage-account #confirmPassword");
    let oldPasswordInput = document.querySelector(".manage-account #oldPasswordInput");
    let oldPassField = document.querySelector(".manage-account #oldPasswordField");
    let submitBtn = document.querySelector(".manage-account #submitBtn");
    let errorInfo = document.querySelector('.manage-account .form-message-error');
    let errorInfoIsNotDisplayed = errorInfo === null || isEmpty(errorInfo);

    newPasswordInput.focused = false;
    confirmPasswordInput.focused = false;
    oldPasswordInput.focused = false;

    if (errorInfoIsNotDisplayed) hide(oldPassField);

    newPasswordInput.onchange = () => {
        if (isNotEmpty(newPasswordInput)) show(oldPassField);
        else hide(oldPassField);
    };

    submitBtn.onclick = () => {
        if (isEmpty(newPasswordInput)) {
            newPasswordInput.required = false;
            confirmPasswordInput.required = false;
            confirmPasswordInput.value = '';
            oldPasswordInput.required = false;
            oldPasswordInput.value = '';
        }
    };
}

const moviePage = document.querySelector(".movie");
if (moviePage != null) {
    const maxRateRange = document
        .querySelector(".movie-stats-container .voting-stats")
        .getAttribute('value');
    const maxPercent = document
        .getElementById("maxPercent")
        .getAttribute('value');
    const starButtons = document.querySelector(".movie-rating-buttons");
    const userIsAuthenticated = starButtons != null;


    for (let i = maxRateRange; i >= 1; i--) {
        let bar = document.getElementById("star-" + i);
        let barValue = bar.getAttribute('value');
        if (barValue === "0") {
            bar.style.width = '0';
        } else {
            let totalBarWidth = barValue * 100.0 / maxPercent;
            bar.style.width = totalBarWidth + '%';
        }
    }

    if (userIsAuthenticated) {
        const userRating = parseInt(starButtons.getAttribute('value'));
        const buttons = document.querySelector('.movie-rating-buttons');

        function fillAllStarsTo(starNum) {
            for (let i = userRating + 1; i <= starNum; i++) {
                fillStar(i);
            }
        }

        function unfillAllStarsTo(starNum) {
            for (let i = userRating + 1; i <= starNum; i++) {
                unfillStar(i);
            }
        }

        function fillStar(starNum) {
            let star = document.getElementById("rating-btn-" + starNum);
            star.style.color = getComputedStyle(document.documentElement)
                .getPropertyValue('--second-text-color');
            star.setAttribute('name', 'star');
        }

        function unfillStar(starNum) {
            let star = document.getElementById("rating-btn-" + starNum);
            star.style.color = getComputedStyle(document.documentElement)
                .getPropertyValue('--main-text-color');
            star.setAttribute('name', 'star-outline');
        }

        buttons.onmouseover = (e) => {
            let target = e.target;
            let targetId = target.id.toString();
            let targetIsStar = targetId.startsWith('rating-btn-');
            let starNum = targetId.replace('rating-btn-','');
            if (targetIsStar) {
                fillAllStarsTo(starNum);
            }
        };

        buttons.onmouseout = (e) => {
            let target = e.target;
            let targetId = target.id.toString();
            let targetIsStar = targetId.startsWith('rating-btn-');
            let starNum = targetId.replace('rating-btn-','');
            if (targetIsStar) {
                unfillAllStarsTo(starNum);
            }
        }
    }

}