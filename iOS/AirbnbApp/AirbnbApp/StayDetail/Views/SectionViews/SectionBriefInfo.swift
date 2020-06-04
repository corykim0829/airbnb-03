import UIKit

class SectionBriefInfo: UIView, ContentView, ViewFromXib {
    static var xibName: String = .init(describing: SectionBriefInfo.self)

    @IBOutlet weak var hostNameLabel: UILabel!
    @IBOutlet weak var roomInfoLabel: UILabel!

    var briefInfo: BriefInfo? = nil {
        didSet { configure() }
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        configure()
    }

    private func configure() {
        guard let briefInfo = briefInfo else { return }

        hostNameLabel.text = briefInfo.hostName
        let roomInfo: [String] = ["\(briefInfo.accommodates) guests", "\(briefInfo.numberOfBedrooms) bedrooms", "\(briefInfo.numberOfBeds) beds", "\(briefInfo.numberOfBathrooms) baths"]
        roomInfoLabel.text = roomInfo.joined(separator: ",")
    }

    func updateChanges() {

    }
}

struct BriefInfo {
    var hostName: String
    var accommodates: Int
    var numberOfBedrooms: Int
    var numberOfBeds: Int
    var numberOfBathrooms: Int
}
