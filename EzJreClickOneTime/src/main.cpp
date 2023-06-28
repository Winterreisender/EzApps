// License: Apache-2.0
// How to compile:
// g++  .\src\main.cpp -o main.exe -lwinhttp -lUrlmon -static -static-libgcc -static-libstdc++ -O2

#include <iostream>
#include <assert.h>
#include <windows.h>
#include <winhttp.h>

using namespace std;


// ---------Config-------------
constexpr auto CFG_UseApiUrl    = false;

// UseApiUrl == true
constexpr auto CFG_InstallerApiUrl_Host = L"api.bell-sw.com";
constexpr auto CFG_InstallerApiUrl_Path = L"/v1/liberica/releases?version-feature=17&version-modifier=latest&bitness=64&release-type=lts&os=windows&arch=x86&package-type=msi&bundle-type=jre-full&output=text&fields=downloadUrl"; 

// UseApiUrl == false
constexpr auto CFG_InstallerUrl = "https://download.bell-sw.com/java/17.0.2+9/bellsoft-jre17.0.2+9-windows-amd64-full.msi";
// ----------------------------


string FetchStr(LPCWSTR host = L"", LPCWSTR path = L"", LPCWSTR method = L"GET") {
  DWORD dwSize = 0;
  DWORD dwDownloaded = 0;
  LPSTR pszOutBuffer;
  BOOL bResults = FALSE;
  HINTERNET hSession = NULL, hConnect = NULL, hRequest = NULL;

  hSession = WinHttpOpen(L"WinHTTP JreClickOneTime/1.0", WINHTTP_ACCESS_TYPE_DEFAULT_PROXY,WINHTTP_NO_PROXY_NAME, WINHTTP_NO_PROXY_BYPASS, 0);
  assert(hSession);

  hConnect = WinHttpConnect(hSession, host, INTERNET_DEFAULT_HTTPS_PORT, 0);
  assert(hConnect);

  // Request handle
  hRequest = WinHttpOpenRequest(hConnect, method, path ,NULL, WINHTTP_NO_REFERER,WINHTTP_DEFAULT_ACCEPT_TYPES, WINHTTP_FLAG_SECURE);
  assert(hRequest);

  // Send
  bResults = WinHttpSendRequest(hRequest, WINHTTP_NO_ADDITIONAL_HEADERS, 0, WINHTTP_NO_REQUEST_DATA, 0, 0, 0);
  assert(bResults);

  // Recv
  bResults = WinHttpReceiveResponse(hRequest, NULL);
  assert(bResults);

  string result = "";
    do {
      // Check data
      dwSize = 0;
      if (!WinHttpQueryDataAvailable(hRequest, &dwSize))
        printf("Error %u in WinHttpQueryDataAvailable.\n", GetLastError());

      // Allocate space
      pszOutBuffer = new char[dwSize + 1];
      if (!pszOutBuffer) {
        cout << "Error! Out of memory" << endl;
        dwSize = 0;
      } else {
        // Read
        ZeroMemory(pszOutBuffer, dwSize + 1);
        if (!WinHttpReadData(hRequest, (LPVOID)pszOutBuffer, dwSize, &dwDownloaded))
          printf("Error %u in WinHttpReadData.\n", GetLastError());
        else {
          result += pszOutBuffer;
        }

        // Free the memory allocated to the buffer.
        delete[] pszOutBuffer;
      }
    } while (dwSize > 0);

  // Report
  if (!bResults)
    printf("Error %d has occurred.\n", GetLastError());

  // Close
  if (hRequest)
    WinHttpCloseHandle(hRequest);
  if (hConnect)
    WinHttpCloseHandle(hConnect);
  if (hSession)
    WinHttpCloseHandle(hSession);

  return result;
}



int main() { 
  cout << "JreClickOneTime v0.1.1" << endl;
  cout << "Downloading Java Runtime Environment(JRE). Please wait ..." << endl;

  string installerUrl;
  if constexpr(CFG_UseApiUrl == true) {
    installerUrl = FetchStr(CFG_InstallerApiUrl_Host,CFG_InstallerApiUrl_Path,L"GET");
  }else{
    installerUrl = CFG_InstallerUrl;
  }

  cout << "Downloading installer: " << installerUrl << endl;
  HRESULT dlResult = URLDownloadToFile(nullptr,installerUrl.c_str(), "./jre-installer.msi", 0 ,nullptr);
  if(dlResult != S_OK) {
    cout << "Download faild! Please check your network and try again." << endl;
    system("pause");
    return 1;
  }

  cout << "Running installer..." << endl;
  WinExec("msiexec /qr /i jre-installer.msi", SW_SHOWMINIMIZED); //SW_SHOWNORMAL SW_HIDE SW_SHOWMINIMIZED

  cout << "Done." << endl;
  return 0; 
}