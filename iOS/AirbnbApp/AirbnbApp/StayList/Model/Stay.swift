import Foundation

struct Stay: Decodable {
    let id: Int
    let images: [String]
    var saved: Bool
    let reviewAverage: Double
    let numberOfReviews: Int
    let isSuperHost: Bool
    let placeType: String
    let city: String
    let state: String
    let title: String
    let price: Int
    let latitude: Double
    let longitude: Double
}
