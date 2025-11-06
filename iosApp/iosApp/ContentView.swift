//
//  ContentView.swift
//  iosApp
//
//  Created by Leonid.Stashevsky on 05.07.2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct ComposeView: UIViewControllerRepresentable {
    let screen: Int32
    
    func makeUIViewController(context: Context) -> UIViewController {
        Main_iosKt.MainViewController(screen: screen)
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    @SceneStorage("selectedTab") private var selectedTabIndex = 0
    @State private var searchText = ""

    var body: some View {
        if #available(iOS 26.0, *) {
            TabView(selection: $selectedTabIndex) {
                Tab("Schedule", systemImage: "clock", value: 0) {
                    ComposeView(screen: 0)
                        .ignoresSafeArea(.all)
                }
                Tab("Speakers", systemImage: "person.2", value: 1) {
                    ComposeView(screen: 1)
                        .ignoresSafeArea(.all)
                }
                Tab("Map", systemImage: "mappin.and.ellipse", value: 2) {
                    ComposeView(screen: 2)
                        .ignoresSafeArea(.all)
                }
                Tab("Info", systemImage: "info.circle", value: 3) {
                    ComposeView(screen: 3)
                        .ignoresSafeArea(.all)
                }
            }.searchable(text: $searchText)
        } else {
            // Fallback on earlier versions
            ComposeView(screen: 0)
                .ignoresSafeArea(.all)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
