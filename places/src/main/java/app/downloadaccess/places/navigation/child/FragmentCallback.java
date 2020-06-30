package app.downloadaccess.places.navigation.child;

import app.downloadaccess.resources.models.Place;

public interface FragmentCallback {
    void onPlaceClicked(Place place);
    void onAddPlaceClicked(Place place);
    void editPlace(Place place);
}
