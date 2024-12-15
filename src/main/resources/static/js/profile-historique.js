document.addEventListener('DOMContentLoaded', () => {
    const forms = document.querySelectorAll('.favorite-form');

    forms.forEach(form => {
        form.addEventListener('submit', async (event) => {
            event.preventDefault();

            const actionUrl = form.getAttribute('action');

            try {
                const response = await fetch(actionUrl, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
            } catch (error) {
                console.error('Erreur lors de l\'ajout aux favoris:', error);
                alert('Impossible d\'ajouter l\'offre aux favoris. Veuillez réessayer.');
            }
        });
    });
});
