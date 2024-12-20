document.addEventListener('DOMContentLoaded', () => {
    const favoriteForms = document.querySelectorAll('.favorite-form');

    favoriteForms.forEach(form => {
        const button = form.querySelector('button');
        button.textContent = 'Supprimer des favoris';
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const forms = document.querySelectorAll('.favorite-form');

    forms.forEach(form => {
        form.addEventListener('submit', async (event) => {
            event.preventDefault();

            const actionUrl = form.getAttribute('action');

            try {
                const response = await fetch(actionUrl, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                if (response.ok) {
                    location.reload();
                }
            } catch (error) {
                console.error('Erreur lors de l\'ajout aux favoris:', error);
                alert('Impossible d\'ajouter l\'offre aux favoris. Veuillez réessayer.');
            }
        });
    });
});
