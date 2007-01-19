/* Licence:
*   Use this however/wherever you like, just don't blame me if it breaks anything.
*
* Credit:
*   If you're nice, you'll leave this bit:
*
*   Class by Pierre-Alexandre Losson -- http://www.telio.be/blog
*   email : plosson@users.sourceforge.net
*
* Some minor modifications done by Gunnar Hillert, gunnar@hillert.com
*/
function refreshProgress()
{
    UploadMonitor.getUploadInfo(updateProgress);
}

function updateProgress(uploadInfo)
{
    if (uploadInfo.inProgress)
    {
        document.getElementById('uploadbutton').disabled = true;
        document.getElementById('file1').disabled = true;

        var fileIndex = uploadInfo.fileIndex;

        var progressPercent = Math.ceil((uploadInfo.bytesRead / uploadInfo.totalSize) * 100);

        document.getElementById('progressBarText').innerHTML = 'upload in progress: ' + progressPercent + '%, transfered ' + uploadInfo.bytesRead + ' of ' + uploadInfo.totalSize + ' bytes';
        
        widthMultiplier = document.getElementById('progressBarBox').clientWidth / 100;
        //alert(widthMultiplier);
        document.getElementById('progressBarBoxContent').style.width = parseInt(progressPercent * widthMultiplier) + 'px';

        window.setTimeout('refreshProgress()', 500);
    }
    else
    {
        
        document.getElementById('uploadbutton').disabled = false;
        document.getElementById('file1').disabled = false;

    }

    return true;
}

function startProgress()
{
    document.getElementById('progressBarBoxContent').style.width = 0 + 'px';
    document.getElementById('progressBarText').innerHTML = 'upload in progress: 0%';
    document.getElementById('uploadbutton').disabled = true;
    // wait a little while to make sure the upload has started ..
    window.setTimeout("refreshProgress()", 1500);
    document.getElementById('progressBar').style.display = 'block';

    return true;
}
