
function Find-Matches {
    param(
        $group
    )

    

    Write-Host finding matches for $group[0]
    $match = $false
foreach($_ in $group[0].ToCharArray())
{
    if($group[1].ToCharArray().Contains($_) -and $group[2].ToCharArray().Contains($_)-and !$match)
    {
        Write-Host searching $_ 
        return $_
        $match = $true
        
    }
}
}